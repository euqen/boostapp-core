package jav.invites;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;
import org.junit.Before;
import org.junit.Test;
import resources.infrastructure.ValidateResult;
import resources.users.User;

import java.util.Map;

/**
 * Created by dima on 3.5.16.
 */
public class TestInviteValidator extends ActionSupport implements SessionAware {

    private ValidateResult result;
    private User user;
    private Map<String, Object> session;

    @Before
    // public void setSession(Map<String, Object> map) {
    //     this.session = session;
    // }

    @Test
    public void validate() {

    }

    public void setSession(Map<String, Object> map) {

    }
}
