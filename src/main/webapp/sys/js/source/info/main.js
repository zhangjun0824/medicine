app.controller('InfoMainCtrl', ['$scope', '$state', '$http', '$timeout', 'toaster','ngDialog',function($scope, $state, $http, $timeout, toaster, ngDialog) {
	
	$scope.infoList;
	$scope.tableList;
	$scope.columnList;
	$scope.table;
	$scope.columnIds;
	$scope.searchVal;
	$scope.columns;
	
	
	$scope.paginationConf = {
		currentPage : 1,// 当前页数
		totalItems : 0,// 总记录数
		itemsPerPage : 10,// 每页显示条数
		pagesLength : 6,// 分页长度
		totalPage : 0,// 总页数
		perPageOptions : [ 10, 20, 30, 40, 50 ],
		onChange : function() {
			$scope.queryInfo();
		}
	};
	
	$.ajax({
		url : webRoot + '/info/main.json',
		type : 'post',
		async : false,
		success:function(data){
			$scope.tableList=data.tables;
		}
	});
	
	$scope.tableChange=function(){
		$.ajax({
			url : webRoot + '/table/queryOneById.json',
			type : 'post',
			data : {id:$scope.tableId},
			async : false,
			success:function(data){
				$scope.table=data.table;
			}
		});
		$.ajax({
			url : webRoot + '/column/queryList.json',
			type : 'post',
			data : {tableId:$scope.tableId},
			async : false,
			success:function(data){
				$scope.columnList=data.columnList;
				$("#columnChosen").html("<option value=''></option>");
				$("#columnChosen").trigger("chosen:updated");
				angular.forEach($scope.columnList, function(data){
					var html="<option value='"+data.id+"'>"+data.memo+"</option>";
					$("#columnChosen").append(html);
				});
				$("#columnChosen").trigger("chosen:updated");
			}
		});
	}
	
	$scope.columnChange=function(){
		
	}
	
	$scope.queryInfo=function(){
		
		if($scope.columnIds){
			$.ajax({
				url : webRoot + '/column/queryListByIds.json',
				type : 'post',
				data : {columnIds:$scope.columnIds.join(",")},
				async : false,
				success:function(data){
					$scope.columns=data.columnList;
				}
			});
			
			$scope.table.columnList=$scope.columns;
			
			$.ajax({
				url : webRoot + '/info/infoList.json',
				type : 'post',
				data : {rows:$scope.paginationConf.itemsPerPage,
						page:$scope.paginationConf.currentPage,
						searchVal:$scope.searchVal,
						table:JSON.stringify($scope.table)},
				async : false,
				success:function(data){
					var list=[];
					angular.forEach(data.infoList,function(data){
						var arr=[];
						for(var i=0;i<$scope.columns.length;i++){
							var name=$scope.columns[i].name.toUpperCase();
							var obj={
								name:data[name]
							}
							obj.id=data.ID;
							arr.push(obj);
						}
						list.push(arr);
					});
					$scope.infoList=list;
					// 设置分页参数
					var page = data.pageInfo;
					$scope.paginationConf.totalItems = page.totalResult;
					$scope.paginationConf.itemsPerPage = page.showCount;
					$scope.paginationConf.totalPage = page.totalPage;
 				}
			});
		}
		
	}
	
	$scope.search = function(e) {
		var keycode = window.event ? e.keyCode : e.which;
		if (keycode == 13) {
			$scope.queryInfo();
		}
	}
	$scope.editInfo = function(infoArr) {
		$scope.table.infoArr=infoArr;
		$state.go("app.infoEdit", {
			table : angular.toJson($scope.table)
		}, {
			reload : true
		});
	}
}]);
