package resources.invites;

import resources.companies.Company;
import resources.companies.CompanyMember;
import resources.companies.CompanyMembersService;
import resources.companies.CompanyService;
import resources.infrastructure.Auth;
import resources.infrastructure.ValidateResult;
import resources.infrastructure.Validator;
import resources.users.User;
import resources.users.UserService;

public class InviteValidator extends Validator {

    public ValidateResult validate(InviteActions params) {
        ValidateResult result = new ValidateResult();
        User user = Auth.getCurrentUser(params.getEmail());

        if (user == null) {
            this.addError("user", "You are not logged in");
        }

        Integer companyId = params.getCompanyId();
        Integer userId = params.getUserId();

        if (companyId == null || userId == null || companyId == 0|| userId == 0) {
            this.addError("all", "Please specify user and company");
            result.errors = this.getErrors();
            return result;
        }

        User invitingUser = UserService.getById(userId);
        if (invitingUser == null) {
            this.addError("user", "this user does not exists");
        }

        Company invitingForCompany = CompanyService.getById(companyId);
        if (invitingForCompany == null) {
            this.addError("company", "this company does not exists");
        }

        if (this.hasErrors()) {
            result.errors = this.getErrors();
            return result;
        }

        Invite isAlreadyInvited = InviteService.getByUserIdAndCompanyId(userId, companyId);

        if (isAlreadyInvited != null) {
            this.addError("invite", "This user already have pending invitation");
            result.errors = this.getErrors();
            return result;
        }

        CompanyMember cm = CompanyMembersService.getUserInCompany(user.getId(), companyId);
        if (cm == null) {
            this.addError("user", "You are not a member of this company");
        } else if (!cm.getRole().contentEquals("creator")) {
            this.addError("user", "You have not enough permissions to create invites for this company");
        }

        if (this.hasErrors()) {
            result.errors = this.getErrors();
            return result;
        }

        CompanyMember userInCompany = CompanyMembersService.getUserInCompany(userId, companyId);
        if (userInCompany != null) {
            this.addError("user", "This user already in the company");
        }


        if (this.hasErrors()) {
            result.errors = this.getErrors();
        }

        return result;
    }
}

