package resources.payments;

import resources.companies.Company;
import resources.companies.CompanyService;
import resources.infrastructure.Auth;
import resources.infrastructure.ValidateResult;
import resources.infrastructure.Validator;
import resources.projects.Project;
import resources.projects.ProjectService;
import resources.users.User;

public class PaymentValidator extends Validator {

    public ValidateResult validate(PaymentActions params) {
        ValidateResult result = new ValidateResult();
        User user = Auth.getCurrentUser(params.getEmail());

        if (user == null) {
            this.addError("user", "You are not logged in");
        }

        Integer userId = params.getUserId();
        Integer projectId = params.getProjectId();
        int amount = params.getAmountInCents();

        if (userId == null || projectId == null ) {
            this.addError("all", "Please select correct project");

            result.errors = this.getErrors();
            return result;
        }

        if (amount <= 0) {
            this.addError("amount", "Payment amount could not be less or equals zero");
        } else if (amount > 500000) {
            this.addError("amount", "Payment amount could not be more than 5000$");
        }

        if (this.hasErrors()) {
            result.errors = this.getErrors();
        }

        Project project = ProjectService.getById(projectId);
        if (project == null) {
            this.addError("project", "This project does not exists");
        }

        if (this.hasErrors()) {
            result.errors = this.getErrors();
        }

        return result;
    }
}

