var app = angular.module('user', [ 'user.UserService', 'user.AccountService' ]);

app.controller('UserController', function($scope, $location, UserService) {

	$scope.users = [];
	
	$scope.load = function() {
		$scope.users = UserService.query();
	};

	$scope.submit = function() {
		if ($scope.user.id === undefined || $scope.user.id === null) {
			UserService.save($scope.user).$promise.then(function(result) {
				alert('Success!');
				$location.path('/');
			}, function(errorMsg) {
				alert(errorMsg.data.reason);
			});
		} else {
			UserService.update($scope.user);
		}
	};

});

app.controller('AccountController',
		function($scope, $location, AccountService) {

			$scope.submit = function() {
				if ($scope.user.id === undefined || $scope.user.id === null) {
					AccountService.save($scope.user).$promise.then(function(
							result) {
						alert('Success!');
						$location.path('/');
					}, function(errorMsg) {
						alert(errorMsg.data.reason);
					});
				}
			};

		});