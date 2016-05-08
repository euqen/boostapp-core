package resources.comments;

import resources.companies.Company;
import resources.infrastructure.Auth;
import resources.infrastructure.ValidateResult;
import resources.infrastructure.Validator;
import resources.projects.Project;
import resources.projects.ProjectService;
import resources.users.User;
import resources.users.UserService;

public class CommentValidator extends Validator {
    public ValidateResult validate(CommentActions params) {
        ValidateResult result = new ValidateResult();
        User user = Auth.getCurrentUser(params.getEmail());

        if (user == null) {
            this.addError("user", "You are not logged in");
            result.errors = this.getErrors();
            return result;
        }

        String commentText = params.getCommentText();
        Integer userId = params.getUserId();
        Integer projectId = params.getProjectId();

        if (userId == null || projectId == null) {
            this.addError("all", "Such user or company not found");
            result.errors = this.getErrors();
            return result;
        }

        User commentCreator = UserService.getById(userId);
        if (commentCreator == null) {
            this.addError("user", "This user does not exists");
        } else if (user.getId() != commentCreator.getId()) {
            this.addError("user", "This is not valid user");
        }

        Project project = ProjectService.getById(projectId);
        if (project == null) {
            this.addError("project", "This project does not exists");
        }

        if (commentText == null) {
            this.addError("commentText", "Comment text could not be empty");
        } else if (commentText.trim().length() > 500) {
            this.addError("commentText", "Comment text could not be more than 500 chars");
        }

        if (this.hasErrors()) {
            result.errors = this.getErrors();
        }

        return result;
    }
}

