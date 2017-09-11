app.controller('TableConfigCtrl', ['$scope', '$http', '$timeout', 'toaster','ngDialog',function($scope, $http, $timeout, toaster, ngDialog) {
	
	$scope.table;
	$scope.tableList;
	$scope.columnList;
	$scope.typeList;
	
	
	$scope.submit=function(){
		if($scope.columnList&&$scope.columnList.length==0){
			toaster.pop("warning","提示","未添加任何字段.");
			return false;
		}else{
			$scope.table.columnList=$scope.columnList;
		}
		var url="";
		if($scope.table&&$scope.table.id){
			url= webRoot + '/table/update.json';
		}else{
			url= webRoot + '/table/save.json';
		}
		$.ajax({
			url : url,
			type : 'post',
			data : {tableStr:JSON.stringify($scope.table)},
			async : false,
			success:function(data){
				toaster.pop("success","提示","提交成功.");
				$scope.queryList();
			},
			error:function(data){
				toaster.pop("error","错误","提交失败,请联系管理员.");
			}
		});
	}
	
	$scope.addTable=function(){
		angular.forEach($scope.tableList,function(data,index){
			data.isSelect=false;
		});
		var table={
				id:'',
				name:'new_table',
				memo:'',
				isSelect:true
		};
		$scope.table=table;
		$scope.tableList.push(table);
		$scope.columnList=[];
	}
	
	$scope.addColumn=function(){
		$scope.columnList.push({
			tableId:$scope.table.id
		});
	}
	
	$scope.delTable=function(index,table){
		ngDialog.open({  
			template: 'tpl/del.html',  
			className: 'ngdialog-theme-default',  
			scope: $scope,  
			controller: function ($scope) {
				$scope.confirm = function () {
					if(table.id){
						$.ajax({
							url : webRoot + '/table/delete.json',
							type : 'post',
							data:{tableId:table.id},
							async : false,
							success:function(data){
								$scope.closeThisDialog();
								$scope.tableList.splice(index,1);
								toaster.pop("success","提示","删除成功.");
								$scope.queryList();
							}
						});
					}else{
						$scope.tableList.splice(index,1);
						$scope.closeThisDialog();
					}
				};  
				$scope.cancel = function () {  
					$scope.closeThisDialog();
				};  
			}  
		});
	}
	$scope.delColumn=function(index,column){
		ngDialog.open({  
            template: 'tpl/del.html',  
            className: 'ngdialog-theme-default',  
            scope: $scope,  
            controller: function ($scope) {
                $scope.confirm = function () {
                	if(column.id){
                		$.ajax({
                			url : webRoot + '/column/delete.json',
                			type : 'post',
                			data:column,
                			async : false,
                			success:function(data){
                				$scope.closeThisDialog();
                				$scope.columnList.splice(index,1);
                				toaster.pop("success","提示","删除成功.");
                			}
                		});
                	}else{
                		$scope.columnList.splice(index,1);
                		$scope.closeThisDialog();
                	}
                };  
                $scope.cancel = function () {  
                    $scope.closeThisDialog();
                };  
            }  
      });
	}
	
	$.ajax({
		url : webRoot + '/dict/queryListByParentCode.json',
		type : 'post',
		async : false,
		data:{"code":"jdbcType"},
		success:function(data){
			$scope.typeList=data.dicts;
		},
		error:function(){
			toaster.pop("error","提示","获取数据类型失败.");
		}
	});
	$scope.selected=function(table,ind){
		angular.forEach($scope.tableList,function(data,index){
			data.isSelect=false;
			if(ind==index){
				data.isSelect=true;
			}
		});
		$scope.table=table;
		if($scope.table.id){
			$.ajax({
				url : webRoot + '/column/queryList.json',
				type : 'post',
				data:{tableId:table.id},
				async : false,
				success:function(data){
					$scope.columnList=data.columnList;
				},
				error:function(){
					toaster.pop("error","提示","查询字段列表失败.");
				}
			});
		}else{
			$scope.columnList=[];
		}
	};
	$scope.queryList=function(){
		$.ajax({
			url : webRoot + '/table/queryList.json',
			type : 'post',
			async : false,
			success:function(data){
				$scope.tableList=data.tableList;
				if($scope.tableList.length>0){
					$scope.table=$scope.tableList[0];
					$scope.table.isSelect=true;
					$scope.selected($scope.table,0);
				}else{
					$scope.table={};
					$scope.columnList=[];
				}
			},
			error:function(){
				toaster.pop("error","提示","获取列表数据失败.");
			}
		});
	};
	$scope.queryList();
	
}]);
