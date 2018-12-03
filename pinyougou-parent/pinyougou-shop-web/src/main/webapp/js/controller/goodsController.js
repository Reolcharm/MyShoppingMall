//控制层 
app.controller('goodsController', function($scope, $controller, goodsService,
		uploadService) {

	$controller('baseController', {
		$scope : $scope
	});// 继承

	// 读取列表数据绑定到表单中
	$scope.findAll = function() {
		goodsService.findAll().success(function(response) {
			$scope.list = response;
		});
	}

	// 分页
	$scope.findPage = function(page, rows) {
		goodsService.findPage(page, rows).success(function(response) {
			$scope.list = response.rows;
			$scope.paginationConf.totalItems = response.total;// 更新总记录数
		});
	}

	// 查询实体
	$scope.findOne = function(id) {
		goodsService.findOne(id).success(function(response) {
			$scope.entity = response;
		});
	}

	// 保存
	$scope.save = function() {
		// 富文本内容保存进 goodsDesc的introduction字段
		$scope.entity.goodsDesc.introduction = editor.html();
		serviceObject = goodsService.add($scope.entity);// 增加
		serviceObject.success(function(response) {
			if (response.success) {
				alert("保存成功!")
				// 还会有继续保存的操作, 所以要清空实体（因为编辑页面无列表）
				$scope.entity = {};
				// 清空富文本内容
				editor.html("");
			} else {
				alert(response.message);
			}
		});
	}

	// 批量删除
	$scope.dele = function() {
		// 获取选中的复选框
		goodsService.dele($scope.selectIds).success(function(response) {
			if (response.success) {
				$scope.reloadList();// 刷新列表
				$scope.selectIds = [];
			}
		});
	}

	$scope.searchEntity = {};// 定义搜索对象

	// 搜索
	$scope.search = function(page, rows) {
		goodsService.search(page, rows, $scope.searchEntity).success(
				function(response) {
					$scope.list = response.rows;
					$scope.paginationConf.totalItems = response.total;// 更新总记录数
				});
	}

	// 上传文件: $scope.image_entity = [{color:'', url:''},{},{}....]
	$scope.uploadFile = function() {
		uploadService.uploadFile().success(function(response) {
			// 整个图片 封装成 image_entity
			// 如果成功, 在页面进行展示: src
			if (response.success) {
				$scope.image_entity.url = response.message;
			} else {
				alert("上传失败,请重拾");
			}

		});
	}
	// 定义页面实体结构 , goods 可省略
	$scope.entity = {
		goods : {},
		goodsDesc : {
			itemImages : []
		}
	};
	// 点击后向图片列表 添加行: 将图片实体(一个集合类型) 添加到页面实体: $scope.entity 中
	$scope.add_image_entity = function() {

		$scope.entity.goodsDesc.itemImages.push($scope.image_entity);
	}

	$scope.del_image_entity = function(index) {

		$scope.entity.goodsDesc.itemImages.splice(index, 1);
	}

});
