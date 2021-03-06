app.controller('CompanyPageController', function ($scope, HomeService, InviteService, CompanyService, ProjectsService, UserService, $routeParams, $location, context) {
    $scope.model = {};
    $scope.context = context.get();
    $scope.projects = [];
    $scope.errors = [];

    $scope.removeProject = function(projectId) {
        if (confirm("Are you sure?")) {
            return ProjectsService.delete({
                id: projectId
                })
                .then(function (data) {
                    if (data.validateErrors && data.validateErrors.length > 0) {
                        $scope.errors = data.validateErrors;
                    } else {
                        $scope.projects = $scope.projects.filter(function (p) {
                            return p.id != projectId;
                        });
                    }
                });
        }
    };

    return CompanyService.getById($routeParams.id)
        .then(function(data) {
            $scope.model = data.data.company;

            if (!$routeParams.id || !$scope.model) {
                $location.path('/404');
            } else {
                return ProjectsService.getProjects($routeParams.id);
            }
        })
        .then(function(projects) {
            if (projects.data.projects) {
                $scope.projects = projects.data.projects;

                return InviteService.getInvitesByCompanyId($routeParams.id);
            }
        })
        .then(function(invites) {
            invites.data.invites.forEach(function(i) {
               return UserService.getById(i.userId)
                   .then(function(user) {
                       i.user = user.data.user;
                   });
            });

            $scope.invites = invites.data.invites;
        });
});
