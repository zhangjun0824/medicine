app.controller('RoleListCtrl', [ '$scope', '$http', '$state', 'toaster',
                                 'ngDialog', function($scope, $http, $state, toaster, ngDialog) {

			$scope.roleList = [];
			$scope.searchVal;

			$scope.paginationConf = {
				currentPage : 1,// 当前页数
				totalItems : 0,// 总记录数
				itemsPerPage : 10,// 每页显示条数
				pagesLength : 6,// 分页长度
				totalPage : 0,// 总页数
				perPageOptions : [ 10, 20, 30, 40, 50 ],
				onChange : function() {
					$scope.queryList();
				}
			};

			$scope.addRole = function() {
				$state.go("app.roleAdd");
			}
			$scope.editRole = function(role) {
				$state.go("app.roleEdit", {
					role : angular.toJson(role)
				}, {
					reload : true
				});
			}
			$scope.delRole = function(role) {
				ngDialog.open({
					template : 'tpl/del.html',
					className : 'ngdialog-theme-default',
					scope : $scope,
					controller : function($scope) {
						$scope.confirm = function() {
							$.ajax({
								url : webRoot + '/role/delete.json',
								type : 'post',
								data : role,
								async : false,
								success : function(data) {
									toaster.pop("success", "提示", "删除成功.");
									$scope.closeThisDialog();
									$scope.queryList();
								}
							});
						};
						$scope.cancel = function() {
							$scope.closeThisDialog();
						};
					}
				});
			}
			$scope.priRole = function(role) {
				$state.go("app.rolePrivilege", {
					role : angular.toJson(role)
				}, {
					reload : true
				});
			}

			$scope.queryList = function() {
				var data = {
					rows : $scope.paginationConf.itemsPerPage,
					page : $scope.paginationConf.currentPage
				}
				if ($scope.searchVal) {
					data.searchVal = $scope.searchVal;
				}
				$.ajax({
					url : webRoot + '/role/queryListPage.json',
					type : 'post',
					data : data,
					async : false,
					success : function(data) {
						$scope.roleList = data.roles;
						var page = data.pageInfo;
						// 设置分页参数
						$scope.paginationConf.totalItems = page.totalResult;
						$scope.paginationConf.itemsPerPage = page.showCount;
						$scope.paginationConf.totalPage = page.totalPage;
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
