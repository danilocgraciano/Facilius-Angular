var app = angular.module('user.AccountService', [ 'ngResource' ]);

app.factory("AccountService", function($resource) {
	return $resource("/facilius/api/account", {
		id : "@id"
	}, {
		update : {
			method : 'PUT'
		}
	});
});