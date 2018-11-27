//控制层 
app.controller('specificationController', function($scope, $controller,
		specificationService) {

	$controller('baseController', {
		$scope : $scope
	});// 继承

	// 读取列表数据绑定到表单中
	$scope.findAll = function() {
		specificationService.findAll().success(function(response) {
			$scope.list = response;
		});
	}

	// 分页
	$scope.findPage = function(page, rows) {
		specificationService.findPage(page, rows).success(function(response) {
			$scope.list = response.rows;
			$scope.paginationConf.totalItems = response.total;// 更新总记录数
		});
	}

	// 查询实体
	$scope.findOne = function(id) {
		specificationService.findOne(id).success(function(response) {
			$scope.entity = response;
		});
	}

	// 保存
	$scope.save = function() {
		var serviceObject;// 服务层对象
		// specification.id 从数据库查询而来.
		if ($scope.entity.specification.id != null) {// 如果有ID
			serviceObject = specificationService.update($scope.entity); // 修改
		} else {
			serviceObject = specificationService.add($scope.entity);// 增加
		}

		serviceObject.success(function(response) {
			if (response.success) {
				// 重新查询
				$scope.reloadList();// 重新加载
			} else {
				alert(response.message);
			}
		});
	}

	// 批量删除
	$scope.dele = function() {
		// 获取选中的复选框
		specificationService.dele($scope.selectIds).success(function(response) {
			if (response.success) {
				$scope.reloadList();// 刷新列表
				$scope.selectIds = [];
			}
		});
	}

	$scope.searchEntity = {};// 定义搜索对象

	// 搜索
	$scope.search = function(page, rows) {
		specificationService.search(page, rows, $scope.searchEntity).success(
				function(response) {
					$scope.list = response.rows;
					$scope.paginationConf.totalItems = response.total;// 更新总记录数
				});
	}

	// entity: 向后台提交的数据结构:
	// 		entity={specification:{id:从数据库返回, specName:编辑页面绑定},
	// 				specificationOptionList : [{},{}]}
	// 		{} --> 就代表了一行输入框.
	// 不在此处写, 写了的话, 每次点击`新建`都会显示上次添加的 选项框.所以给页面 `新建`绑定该初始值.
	// $scope.entity={'specificationOptionList' : []};
	$scope.addTableRow = function() {
		// 集合中增加一组对象.
		$scope.entity.specificationOptionList.push({});
	}

	$scope.deleTableRow = function(idx) {
		// 从集合中移除一组对象.
		$scope.entity.specificationOptionList.splice(idx, 1);
	}
});
