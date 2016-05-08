package resources.projects;

import resources.companies.Company;
import resources.companies.CompanyMember;
import resources.companies.CompanyMembersService;
import resources.companies.CompanyService;
import resources.infrastructure.Auth;
import resources.infrastructure.ValidateResult;
import resources.infrastructure.Validator;
import resources.users.User;

public class ProjectValidator extends Validator {

    public ValidateResult validate(ProjectsAction params) {
        ValidateResult result = new ValidateResult();

        User user = Auth.getCurrentUser(params.getEmail());
        if (user == null) {
            this.addError("user", "You are not logged in");
            result.errors = this.getErrors();
            return result;
        }

        String title = params.getTitle();
        String description = params.getDescription();
        Integer amount = params.getAmount();
        Integer companyId = params.getCompanyId();

        if (title == null || title.length() == 0 || description == null || description.length() == 0
                || amount == null || amount == 0) {
            this.addError("all", "All fields are required");
            result.errors = this.getErrors();
            return result;
        }

        if (title.length() > 100) {
            this.addError("title", "title could not be more than 100 symbols");
        }

        if (description.length() > 200 ) {
            this.addError("desc", "desc could not be more than 500 symbols");
        }

        if (amount > 10000000) {
            this.addError("amount", "amount could not be more than 10000000");
        }

        if (companyId == null) {
            this.addError("company", "Please select company");
        }

        if (this.hasErrors()) {
            result.errors = this.getErrors();
            return result;
        }

        Company company = CompanyService.getById(companyId);
        if (company == null) {
            this.addError("company", "This company does not exists");
            result.errors = this.getErrors();
            return result;
        }

        CompanyMember cm = CompanyMembersService.getUserInCompany(user.getId(), companyId);
        if (cm == null) {
            this.addError("company", "You are not allowed to create or update project for this company");
        } else if(!cm.getRole().contentEquals("creator") && !cm.getRole().contentEquals("manager")) {
            this.addError("permission", "You have not enough permissions to create or update project for this company");
        }

        if (this.hasErrors()) {
            result.errors = this.getErrors();
            return result;
        }

        return result;
    }
}

