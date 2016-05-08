package resources.payments;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.convention.annotation.Result;
import resources.companies.Company;
import resources.infrastructure.ValidateResult;
import resources.projects.Project;
import resources.projects.ProjectService;
import resources.projects.ProjectValidator;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Result(type = "json")
public class PaymentActions extends ActionSupport {
    private int id;

    private List<Payment> payments;
    private Boolean remove;
    private Integer userId;
    private Integer projectId;
    private int amountInCents;
    private String email;

    private List<Map<String, String>> validateErrors;
    private PaymentValidator validator = new PaymentValidator();

    public String execute() throws Exception {
        return this.create();
    }

    public String view() throws Exception {
        if(this.getProjectId() != null) {
            List<Payment> payments = PaymentService.getAllByProjectId(this.getProjectId());
            this.setPayments(payments);
        } else if (this.getUserId() != null) {
            List<Payment> payments = PaymentService.getAllByUserId(this.getUserId());
            this.setPayments(payments);
        }

        return SUCCESS;
    }

    public String create() throws Exception {
        ValidateResult data = validator.validate(this);

        if (data.hasErrors()) {
            this.setValidateErrors(data.errors);
            return SUCCESS;
        }

        PaymentService.save(mapPayment());

        Project project = ProjectService.getById(this.getProjectId());
        project.setPaymentAmount(project.getPaymentAmount() + this.getAmountInCents());
        ProjectService.update(project);

        return SUCCESS;
    }

    private Payment mapPayment() {
        Payment payment = new Payment();

        payment.setId(this.getId());
        payment.setProjectId(this.getProjectId());
        payment.setUserId(this.getUserId());
        payment.setCreatedOn(new Date());
        payment.setAmountInCents(this.getAmountInCents());

        return payment;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getRemove() {
        return this.remove;
    }

    public void setRemove(Boolean remove) {
        this.remove = remove;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getProjectId() {
        return this.projectId;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public List<Payment> getPayments() {
        return this.payments;
    }

    public void setValidateErrors(List<Map<String, String>> errors) {
        this.validateErrors = errors;
    }

    public List<Map<String, String>> getValidateErrors() {
        return this.validateErrors;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer id) {
        this.userId = id;
    }

    public int getAmountInCents() {
        return this.amountInCents;
    }

    public void setAmountInCents(int amountInCents) {
        this.amountInCents = amountInCents;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
