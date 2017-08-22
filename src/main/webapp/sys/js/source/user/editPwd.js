app.controller('UserEditPwdCtrl', [ '$scope', '$http', '$state', 'toaster',
		'$stateParams', function($scope, $http, $state, toaster, $stateParams) {

			$scope.user = angular.fromJson($stateParams.user);

			$scope.submit = function() {
				var user = {
					id: $scope.user.id,
					username: $scope.user.username,
					password : $scope.user.oldPassword
				}
				$.ajax({
					url : webRoot + '/user/checkPwd.json',
					type : 'post',
					data : user,
					async : false,
					success : function(data) {
						var state = data.state;
						if (state.code == 1) {
							user.password=$scope.user.password;
							$.ajax({
								url : webRoot + '/user/updatePwd.json',
								type : 'post',
								data : user,
								async : false,
								success : function(data) {
									var state = data.state;
									if (state.code == 1) {
										toaster.pop("success", "提示", "修改成功.");
										window.history.go(-1);
									} else {
										toaster.pop("error", "错误信息", "提交失败,请联系管理员.");
									}
								},
								error : function() {
									toaster.pop("error", "错误信息", "提交失败,请联系管理员.");
								}
							});
							$state.go("app.userList");
						} else {
							toaster.pop("error", "错误信息", state.msg);
						}
					},
					error : function() {
						toaster.pop("error", "错误信息", "提交失败,请联系管理员.");
					}
				});
			}
		} ]);
