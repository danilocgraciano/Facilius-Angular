var app = angular.module('facilius', [ 'ngRoute', 'login', 'user' ]);

app.run(function($http,$window) {
	  
	  $http.defaults.headers.common['Auth-Token'] = $window.sessionStorage.getItem('faciliusToken');

});

app.config(function($routeProvider, $locationProvider) {

	$routeProvider.when('/', {
		templateUrl : 'static/js/login/login.html',
		controller : 'LoginController',
	}).when('/cadastrar', {
		templateUrl : 'static/js/user/userForm.html',
		controller : 'AccountController',
	}).when('/users', {
		templateUrl : 'static/js/user/userList.html',
		controller : 'UserController',
	}).when('/dashboard', {
		templateUrl : 'static/js/user/dashboard.html',
		controller : 'UserController',
	}).otherwise({
		redirectTo : '/error',
		templateUrl : 'static/partials/404.html'
	});
});
    