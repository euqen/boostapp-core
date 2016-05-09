package resources.comments;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.convention.annotation.Result;
import resources.projects.Project;
import resources.projects.ProjectService;
import resources.users.User;
import resources.users.UserService;

import java.io.*;
import java.util.List;

@Result(type = "json")
public class ExportAllCommentsXlsAction extends ActionSupport {
    private int id;

    private Integer userId;
    private Integer companyId;
    private InputStream inputStream;

    public String view() throws Exception {

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Export Comments");

        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue("id");
        cell = row.createCell(1);
        cell.setCellValue("comment");
        cell = row.createCell(2);
        cell.setCellValue("project");
        cell = row.createCell(3);
        cell.setCellValue("user");


        List<Comment> comments = CommentService.getAll();

        int rowNum = 0;
        for(Comment comment : comments) {
            Project project = ProjectService.getById(comment.getProjectId());
            User user = UserService.getById(comment.getUserId());
            if (project != null && user != null) {
                row = sheet.createRow(++rowNum);
                cell = row.createCell(0);
                cell.setCellValue(comment.getId());
                cell = row.createCell(1);
                cell.setCellValue(comment.getCommentText());
                cell = row.createCell(2);
                cell.setCellValue(project.getTitle());
                cell = row.createCell(3);
                cell.setCellValue(user.getEmail());
            }
        }

        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        workbook.write(baos);
        setInputStream(new ByteArrayInputStream(baos.toByteArray()));

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
