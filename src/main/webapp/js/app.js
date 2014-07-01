'use strict';

angular.module('myApp', ['ngRoute', 'restangular', 'angularFileUpload', 'myApp.services', 'myApp.controllers']).
  config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/import', {templateUrl: 'views/dataimport.html'});
	$routeProvider.when('/filterSelect', {templateUrl: 'views/filterselect.html', controller: 'FilterController'});
	$routeProvider.when('/classAssign', {templateUrl: 'views/classassign.html', controller: 'FakeController'});
	$routeProvider.when('/attrRemoval', {templateUrl: 'views/attrremoval.html', controller: 'FakeController'});
	$routeProvider.when('/testMethod', {templateUrl: 'views/testmethod.html', controller: 'FakeController'});
	$routeProvider.when('/algoSelect', {templateUrl: 'views/algoselect.html', controller: 'FakeController'});
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
  }]);