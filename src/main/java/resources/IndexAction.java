package resources;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

/** Using for app.html rendering (Read single page application) **/
@Results({
        @Result(name = Action.SUCCESS, location = "app.jsp", type = "dispatcher")
})
public class IndexAction extends ActionSupport {
    public String execute() {
        return SUCCESS;
    }
    public String view() {
        return SUCCESS;
    }
    public String update() {
        return SUCCESS;
    }
}
