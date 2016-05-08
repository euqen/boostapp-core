package resources.infrastructure;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.dispatcher.SessionMap;
import resources.users.User;
import resources.users.UserService;

import java.util.Map;

public class Auth {
    public static User getCurrentUser(String email) {
        ActionContext ctx = ActionContext.getContext();
        Map session;
        if (ctx != null) {
            session = ActionContext.getContext().getSession();

            if (session.get("email") != null) {
                return UserService.getByEmail(session.get("email").toString());
            } else {
                return null;
            }
        } else {
            return UserService.getByEmail(email);
        }

    }

    public static User getCurrentUser() {
        ActionContext ctx = ActionContext.getContext();
        Map session;
        if (ctx != null) {
            session = ActionContext.getContext().getSession();

            if (session.get("email") != null) {
                return UserService.getByEmail(session.get("email").toString());
            } else {
                return null;
            }
        }

        return null;
    }

    public static void setCurrentUser(SessionMap session, String email) {
        if (session != null) {
            session.put("email", email);
        }
    }

    public static void unsetCurrentUser(SessionMap session) {
        session.remove("email");
    }
}
