app.controller('InfoEditCtrl', ['$scope', '$state', '$http', '$timeout', 'toaster', 'ngDialog', '$stateParams',function($scope, $state, $http, $timeout, toaster, ngDialog, $stateParams) {
	
	$scope.table=angular.fromJson($stateParams.table);
	var arr=[];
	for(var i=0;i<$scope.table.columnList.length;i++){
		var column=$scope.table.columnList[i];
		var value=$scope.table.infoArr[i];
		var obj={
			id:column.id,
			name:column.name,
			memo:column.memo,
			value:value.name
		}
		arr.push(obj);
	}
	
	$scope.table.infoId=$scope.table.infoArr[0].id;
	
	$scope.table.infoList=arr;
	
	$scope.submit=function(){
		$.ajax({
			url : webRoot + '/info/edit.json',
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
