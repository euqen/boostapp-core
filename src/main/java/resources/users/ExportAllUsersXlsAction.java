package resources.users;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.convention.annotation.Result;
import java.io.*;
import java.util.List;

@Result(type = "json")
public class ExportAllUsersXlsAction extends ActionSupport {
    private int id;

    private Integer userId;
    private Integer companyId;
    private InputStream inputStream;

    public String view() throws Exception {

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Export Users");

        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue("id");
        cell = row.createCell(1);
        cell.setCellValue("email");
        cell = row.createCell(2);
        cell.setCellValue("password");
        cell = row.createCell(3);
        cell.setCellValue("roleId");

        List<User> users = UserService.getAll();

        int rowNum = 0;
        for(User user : users) {
            row = sheet.createRow(++rowNum);
            cell = row.createCell(0);
            cell.setCellValue(user.getId());
            cell = row.createCell(1);
            cell.setCellValue(user.getEmail());
            cell = row.createCell(2);
            cell.setCellValue(user.getPassword());
            cell = row.createCell(3);
            cell.setCellValue(user.getRoleId());
        }

        sheet.autoSizeColumn(1);

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
