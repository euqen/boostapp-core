package resources.projects;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.convention.annotation.Result;
import java.io.*;
import java.util.List;

@Result(type = "json")
public class ExportAllProjectsXlsAction extends ActionSupport {
    private int id;

    private Integer userId;
    private Integer companyId;
    private InputStream inputStream;

    public String view() throws Exception {

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Export Projects");

        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue("id");
        cell = row.createCell(1);
        cell.setCellValue("title");
        cell = row.createCell(2);
        cell.setCellValue("description");
        cell = row.createCell(3);
        cell.setCellValue("amount");
        cell = row.createCell(4);
        cell.setCellValue("paymentAmount");

        List<Project> projects = ProjectService.getAll();

        int rowNum = 0;
        for(Project project : projects) {
            row = sheet.createRow(++rowNum);
            cell = row.createCell(0);
            cell.setCellValue(project.getId());
            cell = row.createCell(1);
            cell.setCellValue(project.getTitle());
            cell = row.createCell(2);
            cell.setCellValue(project.getDescription());
            cell = row.createCell(3);
            cell.setCellValue(project.getAmount());
            cell = row.createCell(4);
            cell.setCellValue(project.getPaymentAmount());
        }

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
