/* 自定义品牌服务层 */
app.service('brandService',
		function($http) {

			/* 显示所有品牌, 已过时 */
			this.findAll = function() {
				return $http.get("../brand/findPage.do");
			}

			/* page 當前頁 rows 每頁顯示條數; 分页显示所有品牌功能 */
			this.findPage = function(page, rows) {
				return $http.get('../brand/findPage.do?page=' + page + '&rows='
						+ rows);
			}

			/* 分页方法的重载. 混合提交 */
			this.search = function(page, rows, searchEntity) {
				return $http.post('../brand/findByExample.do?page=' + page
						+ '&rows=' + rows, searchEntity);
			}

			/* 数据回显功能 */
			this.findOne = function(id) {
				/* 发送 get请求,进行数据回显 */
				return $http.get('../brand/findOne.do?id=' + id);
			}

			/* 新增功能 */
			this.add = function(entity) {
				return $http.post('../brand/add.do', entity);
			}

			/* 修改后, 保存功能 */
			this.update = function(entity) {
				return $http.post('../brand/update.do', entity);
			}

			/* 删除功能 */
			this.dele = function(selectIds) {
				return $http.get("../brand/delete.do?ids=" + selectIds);
			}
		});