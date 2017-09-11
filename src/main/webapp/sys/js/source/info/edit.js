app.controller('InfoEditCtrl', ['$scope', '$http', '$timeout', 'toaster', 'ngDialog', '$stateParams',function($scope, $http, $timeout, toaster, ngDialog, $stateParams) {
	
	$scope.table=angular.fromJson($stateParams.table);
	$scope.infoList;
	var arr=[];
	for(var i=0;i<$scope.table.columnList.length;i++){
		var column=$scope.table.columnList[i];
		var value=$scope.table.infoArr[i];
		var obj={
			name:column.memo,
			value:value.name
		}
		arr.push(obj);
	}
	$scope.infoList=arr;
	
	
	$scope.submit=function(){
		
	}
}]);
