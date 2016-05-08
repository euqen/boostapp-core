package resources.projects;

import resources.companies.CompanyMember;
import resources.companies.CompanyMembersService;
import resources.infrastructure.Auth;
import resources.infrastructure.ValidateResult;
import resources.infrastructure.Validator;
import resources.users.User;

public class ProjectRemoveValidator extends Validator {
    public ValidateResult validate(ProjectsAction params) {
        ValidateResult result = new ValidateResult();

        User user = Auth.getCurrentUser(params.getEmail());

        if (user == null) {
            this.addError("user", "You are not logged in");
            result.errors = this.getErrors();
            return result;
        }

        Integer projectId = params.getId();

        if (projectId == null) {
            this.addError("project", "Please select project");
        }

        Project project = ProjectService.getById(projectId);
        if (project == null) {
            this.addError("project", "Such project does not exists");
            result.errors = this.getErrors();
            return result;
        }

        CompanyMember cm = CompanyMembersService.getUserInCompany(user.getId(), project.getCompanyId());
        if (cm == null) {
            this.addError("permission", "You are not allowed to remove this project");
        } else if (!cm.getRole().contentEquals("creator")) {
            this.addError("permission", "Only company owner or manager can remove projects");
        }

        if (this.hasErrors()) {
            result.errors = this.getErrors();
        }

        return result;
    }
}

