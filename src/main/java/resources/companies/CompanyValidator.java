package resources.companies;

import resources.infrastructure.Auth;
import resources.infrastructure.ValidateResult;
import resources.infrastructure.Validator;
import resources.users.User;

public class CompanyValidator extends Validator {

    public ValidateResult validate(CompanyActions params) {
        ValidateResult result = new ValidateResult();
        User user = Auth.getCurrentUser(params.getEmail());

        if (user == null) {
            this.addError("user", "You are not logged in");
            result.errors = this.getErrors();
            return result;
        }

        if (params.getId() != 0) { //when edit company
            CompanyMember cm = CompanyMembersService.getUserInCompany(user.getId(), params.getId());

            if(cm == null) {
                this.addError("cm", "You are not allowed to edit this company");
            } else if (!cm.getRole().contentEquals("creator")) {
                this.addError("permissions", "You have not enough permissions");
            }
        }

        if (this.hasErrors()) {
            result.errors = this.getErrors();
            return result;
        }

        String title = params.getTitle();
        String tagLine = params.getTagLine();

        if (title == null || title.length() == 0 || tagLine == null || tagLine.length() == 0) {
            this.addError("all", "All fields are required");
            result.errors = this.getErrors();
            return result;
        }

        if (title.length() > 50) {
            this.addError("title", "Title length could not be more than 50 symbols");
        }

        if (tagLine.length() > 50) {
            this.addError("tagLine", "TagLine length could not be more than 50 symbols");
        }

        if (this.hasErrors()) {
            result.errors = this.getErrors();
        }

        return result;
    }
}

