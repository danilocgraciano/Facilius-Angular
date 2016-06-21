var app = angular.module('login');

app.config(function($routeProvider, $locationProvider) {
	$routeProvider.when('/', {
		templateUrl : 'static/js/login/login.html',
		controller : 'LoginController',
	}).otherwise({
		redirectTo : '/',
	});
});
    