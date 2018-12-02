//登陆服务层
app.controller('indexController', function($http, $scope, loginService,$controller) {
	//读取当前登录人  
	$scope.showLoginName=function(){
		loginService.loginName().success(
			function(response){
//定义变量loginName. 页面展示.
				$scope.loginName=response.loginName;
			}			
		);
	}    
});