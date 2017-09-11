app.controller('UserRoleCtrl', [ '$scope', '$http', '$state', 'toaster',
                                 'ngDialog', '$stateParams', function($scope, $http, $state, toaster, ngDialog, $stateParams) {

			$scope.roleList = [];
			$scope.searchVal;
			$scope.user=angular.fromJson($stateParams.user);
			

			$scope.submit = function(){
				var roleUserList=[];
				angular.forEach($scope.roleList, function(data){
					if(data.isChecked){
						var obj={
							userId:$scope.user.id,
							roleId:data.id
						}
						roleUserList.push(obj);
					}
				});
				$.ajax({
					url : webRoot + '/user/saveUserRole.json',
					type : 'post',
					data : {ruList:JSON.stringify(roleUserList),userId:$scope.user.id},
					async : false,
					success : function(data) {
						toaster.pop("success","提示信息","保存成功");
					}
				});
			};

			$scope.queryList = function() {
				$.ajax({
					url : webRoot + '/role/queryListByUser.json',
					type : 'post',
					data : {searchVal:$scope.searchVal,userId:$scope.user.id},
					async : false,
					success : function(data) {
						$scope.roleList = data.roles;
					}
				});
			}

			$scope.search = function(e) {
				var keycode = window.event ? e.keyCode : e.which;
				if (keycode == 13) {
					$scope.queryList();
				}
			}

			$scope.queryList();
		} ]);
