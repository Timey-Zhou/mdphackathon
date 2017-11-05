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

app.controller("byPayer", function($scope, $http) {
  $scope.submit = function (cpt) {
    var path = '/byPayer?' + 'cpt=' + cpt;
    $http.get(path).then(function (response) {
      var rows = [];
      _.forEach(_.keys(response.data), function(insurance) {
        var thisInsuranceClaimCount = response.data[insurance][cpt];
        if(thisInsuranceClaimCount) {
          rows.push({
            insurance: insurance,
            thisInsuranceClaimCount: thisInsuranceClaimCount
          });
        }
      });
      $scope.results = rows;
    });
  }
});