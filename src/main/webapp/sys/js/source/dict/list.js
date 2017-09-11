app.controller('DictListCtrl', ['$scope', '$http', '$state', 'toaster', 'ngDialog', '$timeout', function($scope, $http, $state, toaster, ngDialog, $timeout) {
	
	$scope.searchVal;
	$scope.dict;
	var tree, treedata, flag=true;
	
	$scope.submit=function(){
		var dict = tree.get_selected_branch();
		if(!dict){
        	toaster.pop("warning","警告","请先选择一个字典项.");
        	return false;
		}
		$.ajax({
			url : webRoot + '/dict/checkCode.json',
			type : 'post',
			data:{parentCode:dict.parentCode,code:dict.code,id:dict.id},
			async : false,
			success:function(data){
				var state=data.state;
				if(state.code==0){
					toaster.pop("error","提示",state.msg);
				}else{
					if(dict.id){
						$.ajax({
							url : webRoot + '/dict/update.json',
							type : 'post',
							data:{dict:JSON.stringify(dict)},
							async : false,
							success:function(data){
								toaster.pop("success","提示","提交成功.");
								$scope.queryList();
							},
							error:function(){
								toaster.pop("error","提示","提交失败.");
							}
						});
					}else{
						$.ajax({
							url : webRoot + '/dict/save.json',
							type : 'post',
							data:{dict:JSON.stringify(dict)},
							async : false,
							success:function(data){
								toaster.pop("success","提示","提交成功.");
								$scope.queryList();
							},
							error:function(){
								toaster.pop("error","提示","提交失败.");
							}
						});
					}
				}
			},
			error:function(){
				toaster.pop("error","提示","提交失败.");
			}
		});
	}
	
	$scope.delDict=function(){
        var dict = tree.get_selected_branch();
        if(!dict){
        	toaster.pop("warning","警告","请先选择一个字典.");
        	return false;
        }
        if(dict.id){
        	ngDialog.open({  
        		template: 'tpl/del.html',  
        		className: 'ngdialog-theme-default',  
        		scope: $scope,  
        		controller: function ($scope) {  
        			$scope.confirm = function () {
        				$.ajax({
        					url : webRoot + '/dict/delete.json',
        					type : 'post',
        					data:{id:dict.id,code:dict.code},
        					async : false,
        					success:function(data){
        						toaster.pop("success","提示","删除成功.");
        						$scope.closeThisDialog();
        						$scope.queryList();
        						$scope.try_async_load();
        					}
        				});
        			};  
        			$scope.cancel = function () {  
        				$scope.closeThisDialog();
        			};  
        		}  
        	});
        }else{
        	toaster.pop("success","提示","删除成功.");
        	$scope.queryList();
        	$scope.try_async_load();
        }
	}
	
	$scope.expandOrCollapse=function(){
		flag=!flag;
		if(flag){
			tree.collapse_all();
		}else{
			tree.expand_all();
		}
	}
    $scope.selected = function(branch) {
    	$scope.dict=branch;
    };
    $scope.queryList=function(){
    	$.ajax({
    		url : webRoot + '/dict/queryList.json',
    		type : 'post',
    		async : false,
    		success:function(data){
    			treedata=data.dicts;
    		}
    	});
    };
    $scope.queryList();
    $scope.data = treedata;
    $scope.tree = tree = {};
    $scope.try_async_load = function() {
      $scope.data = [];
      $scope.doing_async = true;
      return $timeout(function() {
    	$scope.data = treedata;
        $scope.doing_async = false;
        return tree.expand_all();
      }, 1000);
    };
    $scope.add_root_branch = function() {
    	  var newB={
  			  name: '新增字典',
  			  selected:true
    	  };
    	  tree.select_branch(tree.add_branch(null,newB));
    };
    $scope.try_adding_a_branch = function() {
      var b = tree.get_selected_branch();
      if(b){
    	  var newB={
    			  name: '新增字典',
    			  parentCode:b.code,
    			  selected:true
    	  };
    	  tree.select_branch(tree.add_branch(b,newB));
      }
    };
	
	
}]);
