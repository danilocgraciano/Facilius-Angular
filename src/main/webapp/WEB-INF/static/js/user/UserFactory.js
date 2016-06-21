var app = angular.module('user.UserService', [ 'ngResource' ]);

app.factory("UserService", function($resource) {
	return $resource("/facilius/api/users", {
		id : "@id"
	}, {
		update : {
			method : 'PUT'
		}
	});
});