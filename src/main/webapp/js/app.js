'use strict';

angular.module('myApp', ['ngRoute', 'restangular', 'angularFileUpload', 'myApp.services', 'myApp.controllers']).
  config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/import', {templateUrl: 'views/dataimport.html'});
	$routeProvider.when('/filterSelect', {templateUrl: 'views/filterselect.html', controller: 'FilterController'});
	$routeProvider.when('/attrRemoval', {templateUrl: 'views/attrremoval.html', controller: 'AttrRemovalController'});
	$routeProvider.when('/testMethod', {templateUrl: 'views/testmethod.html', controller: 'TestingController'});
	$routeProvider.when('/algoSelect', {templateUrl: 'views/algoselect.html', controller: 'AlgorithmController'});
	$routeProvider.when('/decisionTree', {templateUrl: 'views/decisiontree.html', controller: 'TreeController'});
	$routeProvider.when('/analysis', {templateUrl: 'views/analysis.html', controller: 'FakeController'});
	$routeProvider.otherwise({redirectTo: '/import'});
  }]).
  config(['RestangularProvider', function(RestangularProvider) {
      RestangularProvider.setBaseUrl('http://localhost:8080/pest-incidence/api/v1/'); 
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