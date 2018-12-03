//控制层 
app.controller('itemCatController', function($scope, $controller,
		itemCatService, typeTemplateService) {

	$controller('baseController', {
		$scope : $scope
	});// 继承

	// 读取列表数据绑定到表单中
	$scope.findAll = function() {
		itemCatService.findAll().success(function(response) {
			$scope.list = response;
		});
	}

	// 分页
	$scope.findPage = function(page, rows) {
		itemCatService.findPage(page, rows).success(function(response) {
			$scope.list = response.rows;
			$scope.paginationConf.totalItems = response.total;// 更新总记录数
		});
	}

	// 查询实体
	$scope.findOne = function(id) {
		itemCatService.findOne(id).success(function(response) {
			$scope.entity = response;
		});
	}

	// 保存
	$scope.save = function() {
		var serviceObject;// 服务层对象
		if ($scope.entity.id != null) {// 如果有ID
			serviceObject = itemCatService.update($scope.entity); // 修改
		} else {
			// 保存时, 设置每条的上级 ID, 保存到数据库中.
			$scope.entity.parentId = $scope.parentId;
			serviceObject = itemCatService.add($scope.entity);// 增加
		}
		serviceObject.success(function(response) {
			if (response.success) {
				// 重新查询
				// $scope.reloadList();// 重新加载
				// 根据同一上级 id 重新查询 该级数据.
				$scope.findByParentId($scope.parentId);
			} else {
				alert(response.message);
			}
		});
	}

	// 批量删除
	$scope.dele = function() {
		// 获取选中的复选框
		itemCatService.dele($scope.selectIds).success(function(response) {
			if (response.success) {
				$scope.reloadList();// 刷新列表
				$scope.selectIds = [];
			}
		});
	}

	$scope.searchEntity = {};// 定义搜索对象

	// 搜索
	$scope.search = function(page, rows) {
		itemCatService.search(page, rows, $scope.searchEntity).success(
				function(response) {
					$scope.list = response.rows;
					$scope.paginationConf.totalItems = response.total;// 更新总记录数
				});
	}
	// 我们需要一个变量去记住上级 ID，在保存的时候再根据这个 ID 来新增分类
	$scope.parentId = 0;
	// 查询查询为父级 id 的 商品分类
	$scope.findByParentId = function(parentId) {
		// 查询时实现动态赋值 parentId, - - > 下一级的父级 id.
		$scope.parentId = parentId;
		itemCatService.findByParentId(parentId).success(function(response) {
			$scope.list = response;
		});
	}
	// 定义级别
	$scope.grade = 1;
	// 设置级别, 点击查询下一级, 增加1.
	$scope.setGrade = function(value) {
		$scope.grade = value;
	}
	// 控制面包屑.动态.
	// 通过点击查询下一级时,传入当前级别的entity=p_entity,
	// 面包屑部分绑定 entity_1, entity_2, 并在 controller 层进行判断赋值
	$scope.selectList = function(p_entity) {
		// 判断级别
		if ($scope.grade == 1) {
			$scope.entity_1 = null;
			$scope.entity_2 = null;
		}
		if ($scope.grade == 2) {
			$scope.entity_1 = p_entity;
			$scope.entity_2 = null;
		}
		if ($scope.grade == 3) {
			$scope.entity_2 = p_entity;
		}
		// 点击查询下一级
		$scope.findByParentId(p_entity.id);
	}

	// 下拉模板列表的数据来源, 格式
	// tempList={data:[{id(模板id):1,text:"模板名称"},{}]}
	// [{"id":35,"text":"手机"},{"id":37,"text":"电视"}]
	$scope.tempList = {
		data : []
	};
	// 读取模板列表, 从后台获取模板数据, 并赋值给页面.
	$scope.findtempList = function() {
		typeTemplateService.selectOptionList().success(function(response) {
			$scope.tempList = {
				data : response
			}
		});
	}

});
