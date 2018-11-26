/* ---------------初始化控制器, 分层后, 注入自定义服务: brandService-------------------- */
app.controller('baseController', function($scope) {
	
	/* 刷新列表功能 */
	$scope.reloadList = function() {
		/* 由findPage()方法, 变成search() 方法 .调用 controller 层的方法. */
		$scope.search($scope.paginationConf.currentPage,
				$scope.paginationConf.itemsPerPage);
	}
	
	/* 更新 id 数组 */
	// 选中的ID数组
	$scope.selectIds = [];
	$scope.updateSelection = function($event, id) {
		if ($event.target.checked) {
			$scope.selectIds.push(id);
		} else {
			/* 获取到被取消选择的id的索引位置(已被添加到 ID 集合中) */
			var idx = $scope.selectIds.indexOf(id);
			/* 从数组中第几位移除, 移除几位 */
			$scope.selectIds.splice(idx, 1);
		}
	}
	
	/* 分页组件配置 */
	$scope.paginationConf = {
		currentPage : 1,
		totalItems : 10,
		itemsPerPage : 10,
		perPageOptions : [ 10, 20, 30, 40, 50 ],
		onChange : function() {
			$scope.reloadList();// 重新加载
		}
	}

});