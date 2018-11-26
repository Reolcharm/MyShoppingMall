/* -----------初始化控制器, 分层后, 注入自定义服务: brandService------------ */
app.controller('brandController', function($scope, $controller, $http,
		brandService) {

	/* 控制器的继承, 共享$scope */
	$controller('baseController', {$scope : $scope});

	/* page 當前頁 rows 每頁顯示條數 分页显示所有品牌功能 */
	$scope.findPage = function(page, rows) {
		brandService.findPage(page, rows).success(function(data) {
			/* 向视图层赋值 */
			$scope.lists = data.rows;
			// 更新总记录数
			$scope.paginationConf.totalItems = data.total;
		});
	}

	/* 数据回显功能 */
	$scope.findOne = function(id) {
		/* 发送 get请求,进行数据回显 */
		brandService.findOne(id).success(
		/* data: tbBrand */
		function(data) {
			/* 数据回显 */
			$scope.entity = data;
		});
	}

	/*
	 * 修改后, 保存功能 修改原 save() 进行方法的分发:
	 * 
	 * 新增品牌功能
	 */
	$scope.save = function() {
		/* 进行方法的分发 */
		var object = null;
		/* 双向绑定的魅力, 利用`修改`按钮的findOne()对 entity.id 赋值 */
		if ($scope.entity.id != null) {
			object = brandService.update($scope.entity);
		} else {
			object = brandService.add($scope.entity);
		}
		object.success(function(data) {
			/* 新增成功 */
			if (data.success) {
				/* 重新刷新 */
				$scope.reloadList();
			} else {
				alert(data.message);
			}
		});
	}

	/* --------------------批量删除功能开始---------------------- */

	$scope.dele = function() {
		brandService.dele($scope.selectIds).success(function(data) {
			/* 新增成功 */
			if (data.success) {
				/* 重新刷新 */
				$scope.reloadList();
			} else {
				alert(data.message);
			}
		});
	}

	/* --------------------条件查询功能开始---------------------- */
//	初始化搜索结果
	$scope.searchEntity = {};
	/* 分页方法的重载. 混合提交 */
	$scope.search = function(page, rows) {
		brandService.search(page, rows, $scope.searchEntity).success(
				function(data) {
					/* 向视图层列表赋值 */
					$scope.lists = data.rows;
					// 更新总记录数
					$scope.paginationConf.totalItems = data.total;
				});
	}

	/*
	 * 显示所有品牌, 已过时 $scope.findAll = function() {
	 * $http.get("../brand/findPage.do").success( function(data) {
	 * $scope.brandInfos = data; } ) }
	 */

	/* 重新加载功能, 已过时. */
	/*
	 * $scope.reloadList = function() { //切换页码
	 * $scope.findPage($scope.paginationConf.currentPage,
	 * $scope.paginationConf.itemsPerPage); }
	 */

});