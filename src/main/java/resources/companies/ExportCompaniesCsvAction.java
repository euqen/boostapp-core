package resources.companies;

import com.itextpdf.text.*;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.convention.annotation.Result;
import com.itextpdf.text.pdf.PdfWriter;
import resources.projects.Project;
import resources.projects.ProjectService;

import java.io.*;
import java.util.Date;
import java.util.List;

@Result(type = "json")
public class ExportCompaniesCsvAction extends ActionSupport {
    private int id;

    private Integer userId;
    private Integer companyId;
    private InputStream inputStream;

    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";
    private static final String FILE_HEADER = "id,title,tagLine";

    public String view() throws Exception {

        DataOutputStream buffer = new DataOutputStream(new FileOutputStream("report.csv"));

        List<CompanyMember> cms = CompanyMembersService.getByUserId(this.getUserId());
        if (cms != null) {
            buffer.writeBytes(FILE_HEADER);
            buffer.writeBytes(NEW_LINE_SEPARATOR);

            for (CompanyMember cm : cms) {
                Company company = CompanyService.getById(cm.getCompanyId());
                if (company != null) {
                    buffer.writeBytes("" + company.getId());
                    buffer.writeBytes(COMMA_DELIMITER);
                    buffer.writeBytes(company.getTitle());
                    buffer.writeBytes(COMMA_DELIMITER);
                    buffer.writeBytes(company.getTagLine());
                    buffer.writeBytes(NEW_LINE_SEPARATOR);
                }
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
