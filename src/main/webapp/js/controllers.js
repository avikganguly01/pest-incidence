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
          url: 'api/import', 
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
		   window.open('/pest-incidence/api/import', '_blank', '');  
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
  
  controller('FakeController', ['$scope', 'Restangular', function($scope, Restangular) {
	$scope.projects = Restangular.one("import").get();
 }]);