app.controller('UserAddCtrl', ['$scope', '$http', '$state', 'toaster', function($scope, $http, $state, toaster) {
	
	$scope.user={
			name:"",
			idCard:"",
			phone:"",
			email:"",
			username:"",
			password:"",
			password2:""
	};
	$scope.submit=function(){
		$.ajax({
			url : webRoot + '/user/save.json',
			type : 'post',
			data:$scope.user,
			async : false,
			success:function(data){
				var state=data.state;
				if(state.code==1){
					$state.go("app.userList");
				}else{
					toaster.pop("error","错误信息","提交失败,请联系管理员.");
				}
			}
		});
	}
}]);
