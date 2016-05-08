package resources.users;

import resources.infrastructure.ValidateResult;
import resources.infrastructure.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator extends Validator {
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    public ValidateResult validate(UsersActions params) {
        ValidateResult result = new ValidateResult();

        String email = params.getEmail();
        String password = params.getPassword();
        String repeatPassword = params.getRepeatPassword();

        if (password == null || email == null || repeatPassword == null) {
            this.addError("all", "All fields are required");
        }

        if (this.hasErrors()) {
            result.errors = this.getErrors();
            return result;
        }

        if (password.length() < 6 || password.length() > 20) {
            this.addError("password", "Password could not be less 6 or more 20 symbols");
        } else if (!password.contentEquals(repeatPassword)) {
            this.addError("password", "Password should be equals");
        }

        if (!this.validateEmail(email)) {
            this.addError("email", "This email is not valid");
        }

        if (this.hasErrors()) {
            result.errors = this.getErrors();
            return result;
        }

        User user = UserService.getByEmail(email);
        if (user != null) {
            this.addError("user", "User with such email already exists");
            result.errors = this.getErrors();
            return result;
        }

        return result;
    }
}

