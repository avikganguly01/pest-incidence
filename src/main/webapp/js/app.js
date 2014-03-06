'use strict';

angular.module('myApp', ['ngRoute', 'restangular', 'angularFileUpload', 'myApp.services', 'myApp.controllers']).
  config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/import', {templateUrl: 'views/dataimport.html'});
	$routeProvider.when('/varSelect', {templateUrl: 'views/varselect.html', controller: 'FakeController'});
	$routeProvider.when('/phaseSelect', {templateUrl: 'views/phaseselect.html', controller: 'FakeController'});
	$routeProvider.when('/classAssignment', {templateUrl: 'views/classassignment.html', controller: 'FakeController'});
	$routeProvider.when('/labelSelect', {templateUrl: 'views/labelselect.html', controller: 'FakeController'});
	$routeProvider.when('/assignBin', {templateUrl: 'views/assignbin.html', controller: 'FakeController'});
	$routeProvider.when('/attrSelect', {templateUrl: 'views/attrselect.html', controller: 'FakeController'});
	$routeProvider.when('/decisionTree', {templateUrl: 'views/decisiontree.html', controller: 'FakeController'});
	$routeProvider.when('/analysis', {templateUrl: 'views/analysis.html', controller: 'FakeController'});
	$routeProvider.otherwise({redirectTo: '/import'});
  }]).
  config(['RestangularProvider', function(RestangularProvider) {
      RestangularProvider.setBaseUrl('http://localhost:8080/pest-incidence/api'); 
	  RestangularProvider.setDefaultHeaders({ 'Accept': 'text/html,application/json;q=0.9,*/*;q=0.8' });
      RestangularProvider.setRestangularFields({
        id: '_id.$oid'
      });
      
      RestangularProvider.setRequestInterceptor(function(elem, operation, what) {
        if (operation === 'put') {
          elem._id = undefined;
          return elem;
        }
        return elem;
      });
  }]);;