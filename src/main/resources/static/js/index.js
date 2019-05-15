var category=angular.module("category",[]);

category.controller("categoryController",["$scope",function ($scope) {
    $scope.data=["Fryzjer","Masaż","Squash","Dentysta","Mechanik","Grawer"];
}])