app.controller('RoleEditCtrl', ['$scope', '$http', '$state', 'toaster','$stateParams', function($scope, $http, $state, toaster, $stateParams) {
	
	$scope.role=angular.fromJson($stateParams.role);
	
	$scope.submit=function(){
		$.ajax({
			url : webRoot + '/role/update.json',
			type : 'post',
			data:$scope.role,
			async : false,
			success:function(data){
				var state=data.state;
				if(state.code==1){
					toaster.pop("success","提示","提交成功.");
					$state.go("app.roleList");
				}else{
					toaster.pop("error","错误信息","提交失败,请联系管理员.");
				}
			},
			error:function(){
				toaster.pop("error","错误信息","提交失败,请联系管理员.");
			}
		});
	}
}]);
