app.service("uploadService", function($http) {

	this.uploadFile = function() {
		// 对文件的二进制封装 . H5里的代表表单数据
		var formDate = new FormData();
		// 固定属性; //文件上传框的 name 必须等于file ; //files[0] 第一个文件上传框
		formDate.append('file', file.files[0]);

		return $http({
			url : '../upload.do',
			data : formDate,
			method : 'post',
//			指定Content-Type, 默认为 json
			headers : {
				'Content-Type' : undefined
			},
//			AngularJS 表单序列化
			transformRequest : angular.identity
		});
	}

});