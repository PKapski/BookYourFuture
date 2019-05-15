var category=angular.module("category",[]);

category.controller("categoryController",["$scope",function ($scope) {
    $scope.data=["Haircut","Massage","Squash","Dentist","Mechanic","Engraver"];
}])