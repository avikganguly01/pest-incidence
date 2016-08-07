'use strict';

angular.module('myApp.controllers', []).

  controller('ImportController', ['$scope', '$upload', 'Restangular', function($scope, $upload, Restangular) {
       $scope.onFileSelect = function($files) {
	      $scope.selectedFile = $files[0];
	   };
	   $scope.uploadStatus;
	   $scope.uploadMessage;
	   $scope.sendReq = function() {
	     $scope.upload = $upload.upload({
          url: 'api/v1/import', 
          method: "POST",
          file: $scope.selectedFile
         }).success(function(data, status, headers, config) {
        	 $scope.uploadStatus = true;
        	 $scope.uploadMessage = "Successfully Uploaded.";
         }).error(function(data) {
        	 $scope.uploadStatus = false;
        	 $scope.uploadMessage = data;
         });
	   };
	   
  }]).
  
   controller('DownloadController', ['$scope', 'Restangular', function($scope, Restangular) {
	   $scope.sendReq = function() {
		   window.open('/pest-incidence/api/v1/import', '_blank', '');  
	   };
  }]).
  
   controller('FilterController', ['$scope', 'Restangular', function($scope, Restangular) {
	   $scope.attributeTypes = Restangular.all("filter").getList().$object;
	   $scope.filterObjects = [];
	   $scope.filters = [];
	   $scope.filterObject = {};
	   $scope.status;
	   $scope.message;
	   $scope.sendReq = function() {
		   $scope.filterObjects.length = 0;
		   for(var i = 0; i < $scope.filters.length; i++) {
			   if($scope.filters[i] != null) {
				   $scope.filterObject = new filter($scope.attributeTypes[i].attributeName,$scope.filters[i]);
				   $scope.filterObjects.push($scope.filterObject);
			   }
		   }
		   Restangular.all('filter').post($scope.filterObjects).then(function() {
			   $scope.status = true;
			   $scope.message = "Successfully filtered.";
			   
			}, function(response){
				   $scope.status = false;
				   $scope.message = response;
			});
	   };
	   
	   function filter(name, category)
	   {
		   this.name = name;
		   this.category = category;
	   }
	   
  }]).
  controller('AttrRemovalController', ['$scope', 'Restangular', function($scope, Restangular) {
	  $scope.attributeTypes = Restangular.all("attrremoval").getList().$object;
	  $scope.status;
	   $scope.message;
	   $scope.filterObjects = [];
	   $scope.filters = [];
	   $scope.filterObject = {};
	   $scope.sendReq = function() {
		   $scope.filterObjects.length = 0;
		   for(var i = 0; i < $scope.filters.length; i++) {
			   if($scope.filters[i] != null) {
				   $scope.filterObject = new filter($scope.attributeTypes[i].attributeName);
				   $scope.filterObjects.push($scope.filterObject);
			   }
		   }
		   Restangular.all('attrremoval').post($scope.filterObjects).then(function() {
			   $scope.status = true;
			   $scope.message = "Successfully filtered.";
			   
			}, function(response){
				   $scope.status = false;
				   $scope.message = response;
			});
	   };
	   
	   function filter(attributeName)
	   {
		   this.attributeName = attributeName;
	   }
	  
	 }]).
	 controller('AlgorithmController', ['$scope', 'Restangular', function($scope, Restangular) {
			$scope.init = {};
			$scope.init.algorithms = ["J48"];
			$scope.status;
		    $scope.message;
			$scope.classes = [];
		    $scope.addClass = function() {
				$scope.classes.push({minRange:0, maxRange:0});
			};   
		   
			$scope.sendReq = function() {
				
				Restangular.all('algorithm').post($scope.classes).then(function() {
					$scope.status = true;
					   $scope.message = "Successfully filtered.";
				}, function(response) {
					$scope.status = false;
					   $scope.message = response;
				});
			};
		 }]).
 controller('TestingController', ['$scope', 'Restangular', function($scope, Restangular) {
	 	$scope.init = {};
		$scope.init.meths = ["Cross Validation"];
		$scope.status;
	    $scope.message;
	 }]).
 controller('TreeController', ['$scope', 'Restangular', function($scope, Restangular) {
		$scope.projects = Restangular.one("tree").get();
	 }]).
  controller('FakeController', ['$scope', 'Restangular', function($scope, Restangular) {
	$scope.projects = Restangular.one("import").get();
 }]);