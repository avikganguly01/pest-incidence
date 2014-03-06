'use strict';

angular.module('myApp.controllers', []).

  controller('ImportController', ['$scope', '$upload', 'Restangular', function($scope, $upload, Restangular) {
       $scope.onFileSelect = function($files) {
	      $scope.selectedFile = $files[0];
	   };
	   
	   $scope.sendReq = function() {
	     $scope.upload = $upload.upload({
          url: 'api/import', 
          method: "POST",
          file: $scope.selectedFile
         });
	   };
  }]).
  
   controller('FakeController', ['$scope', 'Restangular', function($scope, Restangular) {
	$scope.projects = Restangular.one("import").get();
  }]);