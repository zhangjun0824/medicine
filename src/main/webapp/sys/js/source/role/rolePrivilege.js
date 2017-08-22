app.controller('RolePrivilegeCtrl', [ '$scope', '$http', '$state', 'toaster',
										'$stateParams', 'ngDialog',
										function($scope, $http, $state, toaster, $stateParams,  ngDialog) {

			$scope.role = angular.fromJson($stateParams.role);
			var tree, treedata, flag = true,privileges=[];
			
			
			$scope.submit=function(){
				angular.forEach(treedata, function(data){
					if(data.isChecked){
						privileges.push({privilegeId:data.id,roleId:$scope.role.id});
					}
				});
				$.ajax({
					url : webRoot + '/role/saveRolePrivilege.json',
					type : 'post',
					data:{roleId:$scope.role.id,rpList:JSON.stringify(privileges)},
					async : false,
					success : function(data) {
						$state.go("app.roleList");
						toaster.pop("success","提示","保存成功");
					}
				});
			}
			
			
			$scope.queryPrivilege = function() {
				$.ajax({
					url : webRoot + '/privilege/queryList4RoleId.json',
					type : 'post',
					data:{roleId:$scope.role.id},
					async : false,
					success : function(data) {
						treedata = data.privileges;
					}
				});
			};
			
			$scope.expandOrCollapse = function() {
				flag = !flag;
				if (flag) {
					tree.collapse_all();
				} else {
					tree.expand_all();
				}
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
		} ]);