app.controller('PrivilegeListCtrl', ['$scope', '$http', '$timeout', 'toaster','ngDialog',function($scope, $http, $timeout, toaster, ngDialog) {
	

	$scope.privilege;
	$scope.sourceList;
	var tree, treedata, flag=true;
	
	
	$scope.submit=function(){
		$scope.privilege=$scope.tree.get_selected_branch();
		if($scope.privilege){
			$scope.privilege.resource=$scope.sourceList;
			$.ajax({
				url : webRoot + '/privilege/saveOrUpdate.json',
				type : 'post',
				data:{"privilege":JSON.stringify($scope.privilege)},
				async : false,
				success:function(data){
					toaster.pop("success","提示","提交成功.");
				},
				error:function(){
					toaster.pop("error","提示","提交失败.");
				}
			});
		}
	}
    
    $scope.addSource=function(){
        var privilege = $scope.tree.get_selected_branch();
        if(!privilege){
        	toaster.pop("warning","警告","请先选择一个资源.");
        	return false;
        }
        var source={isMenu:false};
    	if(privilege.id){
    		source.privilegeId=privilege.id;
    	}
    	$scope.sourceList.push(source);
    }
    $scope.delSource = function (index,obj) {  
        ngDialog.open({  
            template: 'tpl/del.html',  
            className: 'ngdialog-theme-default',  
            scope: $scope,  
            controller: function ($scope) {  
                $scope.confirm = function () {
                	if(obj.id){
                		$.ajax({
                			url : webRoot + '/privilege/deleteSource.json',
                			type : 'post',
                			data:obj,
                			async : false,
                			success:function(data){
                				$scope.closeThisDialog();
                				$scope.sourceList.splice(index,1);
                				toaster.pop("success","提示","删除成功.");
                			}
                		});
                	}else{
                		$scope.sourceList.splice(index,1);
                		$scope.closeThisDialog();
                	}
                };  
                $scope.cancel = function () {  
                    $scope.closeThisDialog();
                };  
            }  
       });  
    };  
    $scope.delPrivile = function () {
        var privilege = tree.get_selected_branch();
        if(!privilege){
        	toaster.pop("warning","警告","请先选择一个资源.");
        	return false;
        }
    	ngDialog.open({  
    		template: 'tpl/del.html',  
    		className: 'ngdialog-theme-default',  
    		scope: $scope,  
    		controller: function ($scope) {  
    			$scope.confirm = function () {
    				if(privilege.id){
    					$.ajax({
    						url : webRoot + '/privilege/deletePrivilege.json',
    						type : 'post',
    						data:{"privilege":JSON.stringify(privilege)},
    						async : false,
    						success:function(data){
    							$scope.closeThisDialog();
    							toaster.pop("success","提示","删除成功.");
    							$scope.queryPrivilege();
    							$scope.try_async_load();
    							tree.select_first_branch();
    						},
    						error:function(){
    							toaster.pop("error","错误","删除失败,请联系管理员.");
    						}
    					});
    				}else{
    					toaster.pop("success","提示","删除成功.");
    					$scope.queryPrivilege();
    					$scope.try_async_load();
    					$scope.closeThisDialog();
    				}
    			};  
    			$scope.cancel = function () {  
    				$scope.closeThisDialog();
    			};  
    		}  
    	});
    };  
	$scope.expandOrCollapse=function(){
		flag=!flag;
		if(flag){
			tree.collapse_all();
		}else{
			tree.expand_all();
		}
	}
    $scope.selected = function(branch) {
    	$scope.privilege=branch;
    	if(branch.id){
    		var privilege={
    				id:branch.id
    		}
    		$.ajax({
    			url : webRoot + '/privilege/querySourceList.json',
    			type : 'post',
    			data:privilege,
    			async : false,
    			success:function(data){
    				$scope.sourceList=data.resources;
    			}
    		});
    	}else{
    		$scope.sourceList=[];
    	}
    };
    $scope.queryPrivilege=function(){
    	$.ajax({
    		url : webRoot + '/privilege/queryList.json',
    		type : 'post',
    		async : false,
    		success:function(data){
    			treedata=data.privileges;
    		}
    	});
    };
    $scope.queryPrivilege();
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
			  name: '新增资源',
			  selected:true
	  };
  	  tree.select_branch(tree.add_branch(null,newB));
    };
    $scope.try_adding_a_branch = function() {
      var b = tree.get_selected_branch();
      if(b){
    	  var newB={
    			  name: '新增资源',
    			  parentId:b.id,
    			  parentName:b.name,
    			  selected:true
    	  };
    	  tree.select_branch(tree.add_branch(b,newB));
      }
    };

}]);
