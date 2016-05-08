package resources.companies;

import com.itextpdf.text.*;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.convention.annotation.Result;
import com.itextpdf.text.pdf.PdfWriter;
import resources.projects.Project;
import resources.projects.ProjectService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

@Result(type = "json")
public class CompanyProjectsPdfAction extends ActionSupport {
    private int id;

    private Integer userId;
    private Integer companyId;
    private InputStream inputStream;

    public String view() throws Exception {

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        Document document = new Document();

        PdfWriter.getInstance(document, buffer);

        document.open();
        Paragraph p = new Paragraph();
        Chunk tagline = new Chunk("Boost App - Best Crowdfunding platform ever!");
        tagline.setUnderline(0.1f, -2f);
        tagline.setLineHeight(16);
        p.add(tagline);
        p.setAlignment(Element.ALIGN_RIGHT);
        document.add(p);

        Image logo = Image.getInstance("src/main/webapp/img/logo.png");
        logo.setAlignment(Element.ALIGN_RIGHT);
        document.add(logo);

        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));

        Company company = CompanyService.getById(this.getCompanyId());

        if (company != null) {
            Paragraph par = new Paragraph();
            Chunk label = new Chunk("                                                     " +
                    "Here is company "+ company.getTitle() + " report"
            );

            par.add(label);
            par.setAlignment(Element.ALIGN_MIDDLE);
            document.add(par);

            document.add(new Paragraph(" "));

            List<Project> projects = ProjectService.getByCompanyId(this.getCompanyId());

            for (Project project : projects) {
                document.add(new Paragraph(project.getTitle() + ". Paid " + project.getPaymentAmount() / 100 + "$ of " + project.getAmount() / 100 + "$"));
                document.add(new Paragraph());
            }
            document.add(new Paragraph(""));
            document.add(new Paragraph("Invoice generated at: " + new Date()));
        }

        document.close();

        inputStream = new ByteArrayInputStream(buffer.toByteArray());


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
}
