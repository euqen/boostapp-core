package resources.companies;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.convention.annotation.Result;
import resources.infrastructure.Auth;
import resources.infrastructure.ValidateResult;
import resources.projects.Project;
import resources.projects.ProjectValidator;
import resources.users.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Result(type = "json")
public class CompanyActions extends ActionSupport {
    private int id;

    private List<Company> companies;
    private String title;
    private String tagLine;
    private boolean remove;
    private int userId;
    private Company company;
    private String email;

    private List<Map<String, String>> validateErrors;
    private CompanyValidator validator = new CompanyValidator();

    public String execute() throws Exception {
        return this.create();
    }

    public String view() throws Exception {
        User user = Auth.getCurrentUser();
        if (user != null && user.getIsArchived()) {
            return SUCCESS;
        }
        List<CompanyMember> cms = CompanyMembersService.getByUserId(user.getId());

        List<Company> companies = new ArrayList<Company>();
        for (CompanyMember cm : cms) {
            Company company = CompanyService.getById(cm.getCompanyId());
            if (company != null) {
                companies.add(company);
            }
        }

        this.setCompanies(companies);

        return SUCCESS;
    }

    public String update() throws Exception {
        User user = Auth.getCurrentUser();
        if (user != null && user.getIsArchived()) {
            return SUCCESS;
        }
        if (this.getRemove()) {
            CompanyService.delete(this.getId());
            return SUCCESS;
        }

        ValidateResult data = validator.validate(this);

        if (data.hasErrors()) {
            this.setValidateErrors(data.errors);
            return SUCCESS;
        }

        Company company = CompanyService.update(mapCompany());
        this.setCompany(company);

        return SUCCESS;
    }

    public String create() throws Exception {
        User user = Auth.getCurrentUser(this.getEmail());
        if (user != null && user.getIsArchived()) {
            return SUCCESS;
        }

        ValidateResult data = validator.validate(this);

        if (data.hasErrors()) {
            this.setValidateErrors(data.errors);
            return SUCCESS;
        }

        Company company = CompanyService.save(mapCompany());

        CompanyMember cm = new CompanyMember();
        cm.setUserId(Auth.getCurrentUser(this.getEmail()).getId());
        cm.setCompanyId(company.getId());
        cm.setRole("creator");

        CompanyMembersService.save(cm);
        this.setCompany(company);

        return SUCCESS;
    }

    private Company mapCompany() {
        Company company = new Company();

        company.setId(this.getId());
        company.setTitle(this.getTitle());
        company.setTagLine(this.getTagLine());

        return company;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getRemove() {
        return this.remove;
    }

    public void setRemove(boolean remove) {
        this.remove = remove;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTagLine() {
        return this.tagLine;
    }

    public void setTagLine(String tagLine) {
        this.tagLine = tagLine;
    }

    public void setCompanies(List<Company> companies) {
        this.companies = companies;
    }

    public List<Company> getCompanies() {
        return this.companies;
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

    public Company getCompany() {
        return this.company;
    }

    public void setCompany(Company c) {
        this.company = c;
    }
}
