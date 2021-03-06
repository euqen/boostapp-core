app.controller('SignupController', function ($scope, UserService, $window) {

    $scope.signup = function() {
        const user = {
            email: $scope.email,
            password: $scope.password,
            repeatPassword: $scope.repeatPassword
        };

        return UserService.signup(user)
            .then(function(res) {
                if (res.validateErrors && res.validateErrors.length > 0) {
                    $scope.errors = res.validateErrors;
                } else {
                    $window.location = '/';
                }
            });
    }
});
