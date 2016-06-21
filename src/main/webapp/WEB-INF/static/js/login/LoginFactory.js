var app = angular.module('login.LoginService', [ 'ngResource' ]);

app.service('LoginService', function($http) {
    return {
        login : function(data) {
            return $http.post('/facilius/api/login', data).then(function(response) {
            	$http.defaults.headers.common['Auth-Token'] = token;
                return response.data.token;
            });
        }
    };
});