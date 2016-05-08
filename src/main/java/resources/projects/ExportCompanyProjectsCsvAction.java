package resources.projects;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.convention.annotation.Result;
import java.io.*;
import java.util.List;

@Result(type = "json")
public class ExportCompanyProjectsCsvAction extends ActionSupport {
    private int id;

    private Integer userId;
    private Integer companyId;
    private InputStream inputStream;

    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";
    private static final String FILE_HEADER = "id,title,description,amount,paymentAmount";

    public String view() throws Exception {

        DataOutputStream buffer = new DataOutputStream(new FileOutputStream("report.csv"));

        List<Project> projects = ProjectService.getByCompanyId(this.getCompanyId());

        if (projects != null) {
            buffer.writeBytes(FILE_HEADER);
            buffer.writeBytes(NEW_LINE_SEPARATOR);

            for (Project project : projects) {
                buffer.writeBytes("" + project.getId());
                buffer.writeBytes(COMMA_DELIMITER);
                buffer.writeBytes(project.getTitle());
                buffer.writeBytes(COMMA_DELIMITER);
                buffer.writeBytes(project.getDescription());
                buffer.writeBytes(COMMA_DELIMITER);
                buffer.writeBytes(project.getAmount() == 0 ? "0" : ""+project.getAmount());
                buffer.writeBytes(COMMA_DELIMITER);
                buffer.writeBytes(project.getPaymentAmount() == 0 ? "0" : ""+project.getPaymentAmount());
                buffer.writeBytes(NEW_LINE_SEPARATOR);
            }

        }

        setInputStream(new FileInputStream("report.csv"));

        return SUCCESS;
    }


    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public Integer getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
