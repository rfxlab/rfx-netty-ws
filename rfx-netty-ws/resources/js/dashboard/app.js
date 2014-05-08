// create the module and name it scotchApp
var dashboardApp = angular.module('dashboardApp', [ 'ngRoute' ]);

// configure our routes
dashboardApp.config(function($routeProvider) {
	$routeProvider

	// route for the home page
	.when('/', {
		templateUrl : 'pages/monitor.html',
		controller : 'monitorController'
	})

	.when('/monitor', {
		templateUrl : 'pages/monitor.html',
		controller : 'monitorController'
	})	

	// route for the about page
	.when('/about', {
		templateUrl : 'pages/about.html',
		controller : 'aboutController'
	})

});

dashboardApp.controller('mainController', function($scope) {

});

// create the controller and inject Angular's $scope
dashboardApp.controller('initTabsController', function($scope) {
	$scope.sections = [ {
		key : 'monitor',
		name : 'Monitor'
	}, {
		key : 'about',
		name : 'About'
	} ];

	$scope.setMaster = function(section) {
		$scope.selected = section;
	}

	$scope.isSelected = function(section) {
		return $scope.selected === section;
	}	
	
});

dashboardApp.controller('monitorController', function($scope) {

});

dashboardApp.controller('aboutController', function($scope) {
	
});