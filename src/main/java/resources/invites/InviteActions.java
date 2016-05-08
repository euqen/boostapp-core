package resources.invites;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.convention.annotation.Result;
import resources.companies.Company;
import resources.companies.CompanyMember;
import resources.companies.CompanyMembersService;
import resources.infrastructure.ValidateResult;
import resources.projects.Project;
import resources.projects.ProjectService;
import resources.projects.ProjectValidator;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Result(type = "json")
public class InviteActions extends ActionSupport {
    private int id;

    private List<Invite> invites;
    private int userId;
    private int projectId;
    private String status;
    private String email;

    private List<Map<String, String>> validateErrors;
    private InviteValidator validator = new InviteValidator();

    public String execute() throws Exception {
        return this.create();
    }

    public String view() throws Exception {
        if (this.getCompanyId() != 0) {
            List<Invite> invites = InviteService.getAllByCompanyId(this.getCompanyId());
            this.setInvites(invites);
        } else {
            List<Invite> invites = InviteService.getAllByUserId(this.getUserId());
            this.setInvites(invites);
        }

        return SUCCESS;
    }

    public String create() throws Exception {
        ValidateResult data = validator.validate(this);

        if (data.hasErrors()) {
            this.setValidateErrors(data.errors);
            return SUCCESS;
        }

        InviteService.save(mapInvite());

        return SUCCESS;
    }

    public String update() throws Exception {
        if (this.getStatus() == null || this.getId() == 0) {
            return SUCCESS;
        } else if (this.getStatus().contentEquals("accept")) {
            Invite inv = new Invite();
            inv.setStatus("accepted");
            inv.setId(this.getId());
            Invite invite = InviteService.update(inv);

            CompanyMember cm = new CompanyMember();
            cm.setCompanyId(invite.getCompanyId());
            cm.setUserId(invite.getUserId());
            cm.setRole("staff");

            CompanyMembersService.save(cm);

        } else if(this.getStatus().contentEquals("decline")) {
            Invite inv = new Invite();
            inv.setStatus("declined");
            inv.setId(this.getId());

            InviteService.update(inv);
        }

        return SUCCESS;
    }

    private Invite mapInvite() {
        Invite invite = new Invite();

        invite.setId(this.getId());
        invite.setCompanyId(this.getCompanyId());
        invite.setUserId(this.getUserId());
        invite.setCreatedOn(new Date());
        invite.setStatus("pending");

        return invite;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCompanyId(int projectId) {
        this.projectId = projectId;
    }

    public int getCompanyId() {
        return this.projectId;
    }

    public void setInvites(List<Invite> invites) {
        this.invites = invites;
    }

    public List<Invite> getInvites() {
        return this.invites;
    }

    public void setValidateErrors(List<Map<String, String>> errors) {
        this.validateErrors = errors;
    }

    public List<Map<String, String>> getValidateErrors() {
        return this.validateErrors;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int id) {
        this.userId = id;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String s) {
        this.status = s;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
