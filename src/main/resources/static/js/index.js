var app = angular.module('demo', []);
app.controller("request", function($scope, $http) {
    $scope.submit = function (cpt, insurance) {
        var path = '/request?' + 'cpt=' + cpt + "&insurance=" + insurance;
        $http.get(path).then(function (response) {
            $scope.result = response.data;
        });
    }
});