app.controller('ProjectsController', function ($scope, HomeService, ProjectsService, $routeParams, context, $window) {
    $scope.projects = [];
    $scope.comments = [];

    $scope.context = context.get();

    if($scope.context && $scope.context.isArchived && $window.location.pathname !== '/block') {
        $window.location = '/block';
        return;
    }

    $scope.save = function(model) {
        if (model.isEdit) {
            model.remove = false;
            return ProjectsService.update(model);
        } else {
            return ProjectsService.create(model);
        }
    };

    $scope.remove = function(id) {
        return ProjectsService.update({id: id, remove: true})
            .then(function() {
                $scope.projects = $scope.projects.filter(function(p) {
                    return p.id != id;
                });
            })
    };

    return HomeService.getProjects()
        .then(function(data) {
            $scope.projects = data.data.projects;
        });
});
