app.controller('UserListCtrl', ['$scope', '$http', '$state', 'toaster', 'ngDialog', function($scope, $http, $state, toaster, ngDialog) {
	
	$scope.userList=[];
	$scope.searchVal;
	
	$scope.paginationConf = {
		currentPage : 1,//当前页数
		totalItems : 0,//总记录数
		itemsPerPage : 10,//每页显示条数
		pagesLength : 6,//分页长度
		totalPage : 0,//总页数
		perPageOptions : [ 10, 20, 30, 40, 50 ],
		onChange : function() {
			$scope.queryList();
		}
	};
	
	$scope.addUser=function(){
		$state.go("app.userAdd");
	}
	$scope.editUser=function(user){
		$state.go("app.userEdit",{user:angular.toJson(user)},{reload : true});
	}
	$scope.delUser=function(user){
		 ngDialog.open({  
	        template: 'tpl/del.html',  
	        className: 'ngdialog-theme-default',  
	        scope: $scope,  
	        controller: function ($scope) {  
	            $scope.confirm = function () {
	        		$.ajax({
	        			url : webRoot + '/user/delete.json',
	        			type : 'post',
	        			data:{id:user.id},
	        			async : false,
	        			success:function(data){
	        				toaster.pop("success","提示","删除成功.");
	                        $scope.closeThisDialog();
	        				$scope.queryList();
	        			}
	        		});
	            };  
	            $scope.cancel = function () {  
	                $scope.closeThisDialog();
	            };  
	    	}  
		 });
	}
	$scope.resetUser=function(obj){
		var user = {
				id: obj.id,
				username: obj.username,
				password : obj.password
			}
		ngDialog.open({  
			template: 'tpl/del.html',  
			className: 'ngdialog-theme-default',  
			scope: $scope,  
			controller: function ($scope) {  
				$scope.confirm = function () {
					$.ajax({
						url : webRoot + '/user/resetPwd.json',
						type : 'post',
						data:user,
						async : false,
						success:function(data){
							toaster.pop("success","提示","重置成功. 密码与用户名相同.");
							$scope.closeThisDialog();
						}
					});
				};  
				$scope.cancel = function () {  
					$scope.closeThisDialog();
				};  
			}  
		});
	}
	$scope.roleUser=function(user){
		$state.go("app.userRole", {
			user : angular.toJson(user)
		}, {
			reload : true
		});
	}
	$scope.queryList=function(){
		var data={
			rows:$scope.paginationConf.itemsPerPage,
			page:$scope.paginationConf.currentPage
		}
		if($scope.searchVal){
			data.searchVal=$scope.searchVal;
		}
		$.ajax({
			url : webRoot + '/user/queryListPage.json',
			type : 'post',
			data:data,
			async : false,
			success:function(data){
				$scope.userList=data.users;
				var page = data.pageInfo;
				//设置分页参数
				$scope.paginationConf.totalItems = page.totalResult;
				$scope.paginationConf.itemsPerPage = page.showCount;
				$scope.paginationConf.totalPage = page.totalPage;
			}
		});
	}
	
	$scope.search=function(e){
        var keycode = window.event ? e.keyCode :e.which;
        if(keycode == 13) {
        	$scope.queryList();
        }
	}
	
	$scope.queryList();
}]);
