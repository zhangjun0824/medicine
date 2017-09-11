app.controller('InfoAddCtrl', ['$scope', '$state', '$http', '$timeout', 'toaster', 'ngDialog', '$stateParams',function($scope, $state, $http, $timeout, toaster, ngDialog, $stateParams) {
	
	$scope.table=angular.fromJson($stateParams.table);
	
	$scope.submit=function(){
		$.ajax({
			url : webRoot + '/info/save.json',
			type : 'post',
			data : {tableStr:JSON.stringify($scope.table)},
			async : false,
			success:function(data){
				toaster.pop("success","提示","修改成功.");
				$state.go("app.info");
			}
		});
	}
}]);
