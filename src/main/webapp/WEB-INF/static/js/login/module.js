var app = angular.module('login', []);

app.controller('LoginController', function($scope, $location, $window, $http, LoginService) {
	
	$scope.submit = function() {

//      LOGIN WITHOUT TOKEN
//		LoginFactory.query({'data' : $scope.user}, function(result) {
//			if (result[0] === undefined){
//				alert('User Invalid!');
//			}else{
//				alert('Success!');
//			}
//          }, function(errorMsg) {
//        	  alert(errorMsg.data.reason);
//          });
		
		LoginService.login($scope.user).then(function(token) {
			$location.path('/dashboard');
        }, function(errorMsg) {
        	alert(errorMsg.data.reason);
        });
	};

});
