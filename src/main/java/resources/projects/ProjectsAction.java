package resources.projects;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.convention.annotation.Result;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import resources.infrastructure.Auth;
import resources.infrastructure.ValidateResult;
import resources.users.User;
import resources.users.UserValidator;

import java.util.List;
import java.util.Map;

@Result(type = "json")
public class ProjectsAction extends ActionSupport {
    private static final long serialVersionUID = 9037336532369476225L;
    private static final Logger log = LogManager.getLogger(ProjectsAction.class);

    private int id;
    private Integer companyId;

    private List<Project> projects;
    private String title;
    private String description;
    private Integer amount;
    private boolean remove;
    private String email;

    private List<Map<String, String>> validateErrors;
    private ProjectValidator validator = new ProjectValidator();
    private ProjectRemoveValidator removeValidator = new ProjectRemoveValidator();

    public String execute() throws Exception {
        return this.create();
    }

    public String view() throws Exception {
        User user = Auth.getCurrentUser(this.getEmail());
        if (user != null && user.getIsArchived()) {
            return SUCCESS;
        }

        if (this.getCompanyId() != null) {
            List<Project> projects = ProjectService.getByCompanyId(this.getCompanyId());
            this.setProjects(projects);
        } else {
            List<Project> projects = ProjectService.getAll();
            this.setProjects(projects);
        }

        return SUCCESS;
    }

    public String update() throws Exception {
        User user = Auth.getCurrentUser(this.getEmail());
        if (user != null && user.getIsArchived()) {
            return SUCCESS;
        }

        if (this.getRemove()) {
            ValidateResult data = removeValidator.validate(this);

            if (data.hasErrors()) {
                this.setValidateErrors(data.errors);
                return SUCCESS;
            }

            ProjectService.delete(this.getId());
        } else {
            ValidateResult data = validator.validate(this);

            if (data.hasErrors()) {
                this.setValidateErrors(data.errors);
                return SUCCESS;
            }

            ProjectService.update(mapProject());
        }

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

        ProjectService.save(mapProject());

        return SUCCESS;
    }

    private Project mapProject() {
        Project project = new Project();

        project.setId(this.getId());
        project.setTitle(this.getTitle());
        project.setDescription(this.getDescription());
        project.setAmount(this.getAmount());
        project.setCompanyId(this.getCompanyId());

        return project;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(Integer id) {
        this.companyId = id;
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

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getAmount() {
        return this.amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public List<Project> getProjects() {
        return this.projects;
    }

    public void setValidateErrors(List<Map<String, String>> errors) {
        this.validateErrors = errors;
    }

    public List<Map<String, String>> getValidateErrors() {
        return this.validateErrors;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
