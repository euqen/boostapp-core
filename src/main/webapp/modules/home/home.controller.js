app.controller('HomeController', function ($scope, HomeService, $rootScope, $window) {
    if($scope.context && $scope.context.isArchived && $window.location.pathname !== '/block') {
        $window.location = '/block';
        return;
    }

    return HomeService.getProjects()
        .then(function(data) {
            $scope.projects = data.data.projects;
        });
});
