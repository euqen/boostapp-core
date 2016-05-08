package resources.users;

import resources.infrastructure.Auth;
import resources.infrastructure.ValidateResult;
import resources.infrastructure.Validator;
import resources.projects.ProjectsAction;

public class UserBlockValidator extends Validator {

    public ValidateResult validate(UsersActions params) {
        ValidateResult result = new ValidateResult();
        Integer userId = params.getId();

        User user = Auth.getCurrentUser(params.getEmail());

        if (user == null) {
            this.addError("user", "You are not logged in");
        } else {
            if (user.getRoleId() != 24) {
                this.addError("user", "You have not enough permissions");
            }
        }

        if (this.hasErrors()) {
            result.errors = this.getErrors();
            return result;
        }

        if (userId == null) {
            this.addError("user", "Please select user");
        } else {
            User blockingUser = UserService.getById(userId);

            if (blockingUser == null) {
                this.addError("user", "This user does not exists");
            } else if (blockingUser.getId() == user.getId()) {
                this.addError("user", "You cant block or unblock yourself");
            }
        }

        if (this.hasErrors()) {
            result.errors = this.getErrors();
        }

        return result;
    }

}
