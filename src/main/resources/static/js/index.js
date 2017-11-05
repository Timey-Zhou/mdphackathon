var app = angular.module('demo', []);
app.controller("singleRequest", function($scope, $http) {
    $scope.submit = function (cpt, insurance) {
        var path = '/singleRequest?' + 'cpt=' + cpt + "&insurance=" + insurance;
        $http.get(path).then(function (response) {
            $scope.result = response.data;
        });
    }
});

app.controller("batchRequest", function($scope, $http) {
    $scope.submit = function (cpt) {
        var path = '/batchRequest?' + 'cpt=' + cpt;
        $http.get(path).then(function (response) {
            $scope.results = response.data;
        });
    }
});