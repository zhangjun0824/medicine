app.controller('UserEditCtrl', [ '$scope', '$http', '$state', 'toaster',
		'$stateParams', function($scope, $http, $state, toaster, $stateParams) {

			$scope.user = angular.fromJson($stateParams.user);

			if ($scope.user && $scope.user.attachmentId) {
				$.ajax({
					url : webRoot + '/sys/attachmentContentList.json',
					type : 'post',
					data : {
						attachmentId : $scope.user.attachmentId
					},
					async : false,
					success : function(data) {
						var contentList = data.contentList;
						$scope.user.img = webRoot + contentList[0].url;
					}
				});
			}
			;

			$scope.submit = function() {
				var form = new FormData();
				var file = document.querySelector('input[type=file]').files[0];
				;
				if (file != null) {
					form.append('file', file);
					form.append('id', $scope.user.attachmentId);
					if ($scope.user.attachmentId) {
						$.ajax({
							url : webRoot + '/sys/delAttachment.json',
							type : 'post',
							data : {
								attachmentId : $scope.user.attachmentId
							},
							async : false,
							success : function(data) {
							}
						});
					}
					$http({
						method : 'POST',
						url : webRoot + "/sys/upload.json",
						data : form,
						headers : {
							'Content-Type' : undefined
						},
						transformRequest : angular.identity
					}).success(function(response) {
						$scope.user.attachmentId = response.att.id
					});
				}
				var user = {
					id : $scope.user.id,
					name : $scope.user.name,
					idCard : $scope.user.idCard,
					phone : $scope.user.phone,
					email : $scope.user.email,
					attachmentId : $scope.user.attachmentId
				}
				$.ajax({
					url : webRoot + '/user/update.json',
					type : 'post',
					data : user,
					async : false,
					success : function(data) {
						var state = data.state;
						if (state.code == 1) {
							toaster.pop("success", "提示", "提交成功.");
							$state.go("app.userList");
						} else {
							toaster.pop("error", "错误信息", "提交失败,请联系管理员.");
						}
					},
					error : function() {
						toaster.pop("error", "错误信息", "提交失败,请联系管理员.");
					}
				});
			}
		} ]);
function AutoResizeImage(maxWidth, maxHeight, objImg) {
	var img = new Image();
	img.src = objImg.src;
	var hRatio;
	var wRatio;
	var Ratio = 1;
	var w = img.width;
	var h = img.height;
	wRatio = maxWidth / w;
	hRatio = maxHeight / h;
	if (maxWidth == 0 && maxHeight == 0) {
		Ratio = 1;
	} else if (maxWidth == 0) {//
		if (hRatio < 1)
			Ratio = hRatio;
	} else if (maxHeight == 0) {
		if (wRatio < 1)
			Ratio = wRatio;
	} else if (wRatio < 1 || hRatio < 1) {
		Ratio = (wRatio <= hRatio ? wRatio : hRatio);
	}
	if (Ratio < 1) {
		w = w * Ratio;
		h = h * Ratio;
	}
	objImg.height = h;
	objImg.width = w;
}