package resources.payments;

import com.itextpdf.text.*;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.convention.annotation.Result;
import com.itextpdf.text.pdf.PdfWriter;
import resources.projects.Project;
import resources.projects.ProjectService;
import resources.users.User;
import resources.users.UserService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Result(type = "json")
public class PaymentCheckAction extends ActionSupport {
    private int id;

    private Integer userId;
    private Integer paymentId;
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

        Paragraph par = new Paragraph();
        Chunk label = new Chunk("                                                            " +
                "Here is you payment check"
        );
        par.add(label);
        par.setAlignment(Element.ALIGN_MIDDLE);
        document.add(par);

        document.add(new Paragraph(" "));

        User user = UserService.getById(this.getUserId());
        Payment payment = PaymentService.getById(this.getPaymentId());
        Project project = ProjectService.getById(payment.getProjectId());

        if (user != null && payment != null && project != null) {
            document.add(new Paragraph("Check #" + payment.getId()));
            document.add(new Paragraph("User " + user.getEmail() + " paid " +
                    (payment.getAmountInCents() / 100) + "$ for crowdfunding project " + project.getTitle()));

            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Check generated at: " + new Date()));
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

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public Integer getPaymentId() {
        return this.paymentId;
    }
}
