package jav.users;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionProxy;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsTestCase;
import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.RequestAware;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import resources.infrastructure.Auth;
import resources.users.User;
import resources.users.UserService;
import resources.users.UsersActions;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class TestUser extends StrutsTestCase implements RequestAware {
    private User user;

    private ActionContext actionContext;
    private String email = "e.shilin@paralect.com";
    private String blockedUser = "n.dubko@bsuir.by";
    private String password = "qwerty";
    private int roleId = 55;
    private boolean isArchived = true;
    public UsersActions usersActions = new UsersActions();
    private Map<String, Object> request;

    public void setRequest(Map<String,Object> request) {
        this.request = request;
    }

    public Map<String, Object> getRequest() {
        return this.request;
    }


    @Before
    public void setUp() throws Exception {
        user = new User();
        user.setEmail(email);
        user.setIsArchived(isArchived);
        user.setPassword(password);
        user.setRoleId(roleId);
    }

    @Test
    public void getId() throws Exception {
        assertNotNull(user.getId());
    }

    @Test
    public void getEmail() throws Exception {
        assertEquals(user.getEmail(), email);
    }

    @Test
    public void getPassword() throws Exception {
        assertEquals(user.getPassword(), password);
    }

    @Test
    public void getRoleId() throws Exception {
        assertEquals(user.getRoleId(), roleId);
    }

    @Test
    public void getIsArchived() throws Exception {
        assertEquals(user.getIsArchived(), isArchived);
    }

    @Test
    public void testLogInValidateErrors() throws Exception {
        usersActions.setEmail("pp@pp.rr");
        usersActions.setPassword("12123");

        usersActions.update();

        assertEquals(usersActions.getValidateErrors().get(0).get("user"), "Such user does not exists"); //does not exists
    }

    @Test
    public void testLogInValidateErrorsWithoutEmail() throws Exception {
        usersActions.setPassword("12123");

        usersActions.update();

        assertEquals(usersActions.getValidateErrors().get(0).get("all"), "All fields are required");
    }

    @Test
    public void testLogInValidateErrorsWithoutPassword() throws Exception {
        usersActions.setEmail("pp@pp.rr");

        usersActions.update();

        assertEquals(usersActions.getValidateErrors().get(0).get("all"), "All fields are required");
    }

    @Test
    public void testLogInValidateErrorsWithoutAllData() throws Exception {
        usersActions.update();

        assertEquals(usersActions.getValidateErrors().get(0).get("all"), "All fields are required");
    }

    @Test
    public void testLogInSuccess() throws Exception {
        usersActions.setEmail(this.email);
        usersActions.setPassword("qweqwe");

        usersActions.update();

        assertEquals(usersActions.getValidateErrors(), null); //no errors
    }

    @Test
    public void testLogInPasswordFail() throws Exception {
        usersActions.setEmail(this.email);
        usersActions.setPassword("qdgffweqwe");

        usersActions.update();

        assertEquals(usersActions.getValidateErrors().get(0).get("user"), "Invalid credentials"); //invalid credentials
    }

    @Test
    public void testUserBlockNotExists() throws Exception {
        usersActions.setBlock("block");
        usersActions.setEmail(this.email);
        usersActions.setId(1321321);

        usersActions.update();

        assertEquals(usersActions.getValidateErrors().get(0).get("user"), "This user does not exists"); //user does not exists
    }

    @Test
    public void testUserSelfBlock() throws Exception {
        usersActions.setBlock("block");
        usersActions.setEmail(this.email);
        usersActions.setId(24);

        usersActions.update();

        assertEquals(usersActions.getValidateErrors().get(0).get("user"), "You cant block or unblock yourself");
    }

    @Test
    public void testUserBlockWithoutPermissions() throws Exception {
        usersActions.setBlock("block");
        usersActions.setEmail(this.blockedUser);
        usersActions.setId(58);

        usersActions.update();

        assertEquals(usersActions.getValidateErrors().get(0).get("user"), "You have not enough permissions");
    }

    @Test
    public void testUserBlockWithoutId() throws Exception {
        usersActions.setBlock("block");
        usersActions.setEmail(this.email);

        usersActions.update();

        assertEquals(usersActions.getValidateErrors().get(0).get("user"), "Please select user");
    }

    @Test
    public void testSignupUserWithoutCreds() throws Exception {
        usersActions.create();

        assertEquals(usersActions.getValidateErrors().get(0).get("all"), "All fields are required");
    }

    @Test
    public void testSignupUserWithoutPasswords() throws Exception {
        usersActions.setEmail("faasffas");
        usersActions.create();

        assertEquals(usersActions.getValidateErrors().get(0).get("all"), "All fields are required");
    }

    @Test
    public void testSignupUserPasswordLengthIncorrect() throws Exception {
        usersActions.setEmail("faasffas");
        usersActions.setPassword("q");
        usersActions.setRepeatPassword("q");
        usersActions.create();

        assertEquals(usersActions.getValidateErrors().get(0).get("password"), "Password could not be less 6 or more 20 symbols");
    }

    @Test
    public void testSignupUserPasswordEqualsIncorrect() throws Exception {
        usersActions.setEmail("faasffas");
        usersActions.setPassword("qweqwe");
        usersActions.setRepeatPassword("q");
        usersActions.create();

        assertEquals(usersActions.getValidateErrors().get(0).get("password"), "Password should be equals");
    }

    @Test
    public void testSignupUserEmailIncorrect() throws Exception {
        usersActions.setEmail("faasffas");
        usersActions.setPassword("qweqwe");
        usersActions.setRepeatPassword("qweqwe");
        usersActions.create();

        assertEquals(usersActions.getValidateErrors().get(0).get("email"), "This email is not valid");
    }

    @Test
    public void testSignupUserEmailUserAlreadyExists() throws Exception {
        usersActions.setEmail(this.email);
        usersActions.setPassword("qweqwe");
        usersActions.setRepeatPassword("qweqwe");
        usersActions.create();

        assertEquals(usersActions.getValidateErrors().get(0).get("user"), "User with such email already exists");
    }

    @Test
    public void testSignupUserSuccess() throws Exception {
        usersActions.setEmail("ThisIsSuperUniqueEmail@paralect.com");
        usersActions.setPassword("securePassword123");
        usersActions.setRepeatPassword("securePassword123");
        usersActions.create();

        assertEquals(usersActions.getValidateErrors(), null);
    }

    @Test
    public void testLogInValidateErrorsWithoutData() throws Exception {
        usersActions.setPassword("12123");

        usersActions.update();

        assertEquals(usersActions.getValidateErrors().get(0).get("all"), "All fields are required");
    }

    @After
    public void tearDown() throws Exception {
        User user = UserService.getByEmail("ThisIsSuperUniqueEmail@paralect.com");
        if (user != null) {
            UserService.delete(user.getId());
        }
    }
}
