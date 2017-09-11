app.controller('RoleAddCtrl', ['$scope', '$http', '$state', 'toaster', function($scope, $http, $state, toaster) {
	
	$scope.role={
			name:"",
			code:"",
			memo:""
	};
	$scope.submit=function(){
		$.ajax({
			url : webRoot + '/role/save.json',
			type : 'post',
			data:$scope.role,
			async : false,
			success:function(data){
				var state=data.state;
				if(state.code==1){
					$state.go("app.roleList");
				}else{
					toaster.pop("error","错误信息","提交失败,请联系管理员.");
				}
			}
		});
	}
}]);
