package resources.users;

import com.itextpdf.text.*;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.convention.annotation.Result;
import com.itextpdf.text.pdf.PdfWriter;
import resources.payments.Payment;
import resources.payments.PaymentService;
import resources.users.User;
import resources.users.UserService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

@Result(type = "json")
public class UserDetailsPdfAction extends ActionSupport {
    private int id;

    private Integer userId;
    private Integer projectId;
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

        User user = UserService.getById(this.getUserId());

        if (user != null) {
            Paragraph par = new Paragraph();
            Chunk label = new Chunk("                                                     " +
                    "Here is user #"+ user.getId() + "'s details report"
            );

            par.add(label);
            par.setAlignment(Element.ALIGN_MIDDLE);
            document.add(par);

            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));

            Chunk title = new Chunk("Email: " + user.getEmail());
            title.setUnderline(0.1f, -2f);
            document.add(new Paragraph(title));
            Chunk phone = new Chunk("Phone: 35345345345");
            title.setUnderline(0.1f, -2f);
            document.add(new Paragraph(phone));

            document.add(new Paragraph("Generated at: " + new Date()));
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

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getProjectId() {
        return this.projectId;
    }
}
