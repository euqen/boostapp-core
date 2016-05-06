app.controller('CompaniesController', function ($scope, HomeService, CompanyService, $window, $routeParams, $location, context) {
    $scope.model = {};
    $scope.companies = [];

    $scope.context = context.get();
    if (!$scope.context) {
        return $location.path('/404');
    }

    if($scope.context && $scope.context.isArchived && $window.location.pathname !== '/block') {
        $window.location = '/block';
    }

    return CompanyService.getCompanies()
        .then(function(data) {
            $scope.companies = data.data.companies;

            if ($routeParams.id) {
                $scope.model = $scope.companies.find(function(p) {
                    if(+p.id === +$routeParams.id) {
                        $routeParams.action === 'edit' ? p.isEdit = true : p.view = true;
                        return p;
                    }
                });
            }
        });
});
