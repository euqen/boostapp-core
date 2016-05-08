package jav.companies;

import org.junit.Before;
import org.junit.Test;
import resources.companies.Company;
import resources.companies.CompanyActions;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestCompany {

    private Company company;

    private String title = "Boot";
    private Date createdOn = new Date(2014, 9, 1);
    private String tagLine = "ACTIVE";
    private String email = "e.shilin@paralect.com";
    public CompanyActions companyActions = new CompanyActions();

    @Before
    public void setUp() throws Exception {
        company = new Company();
        company.setCreatedOn(createdOn);
        company.setTagLine(tagLine);
        company.setTitle(title);
    }

    @Test
    public void getId() throws Exception {
        assertNotNull(company.getId());
    }

    @Test
    public void getTitle() throws Exception {
        assertEquals(company.getTitle(), title);
    }

    @Test
    public void getCreatedOn() throws Exception {
        assertEquals(company.getCreatedOn(), createdOn);
    }

    @Test
    public void getTagLine() throws Exception {
        assertEquals(company.getTagLine(), tagLine);
    }

    @Test
    public void testCreateCompany() throws Exception {
        companyActions.setEmail(this.email);
        companyActions.setTagLine("tag");
        companyActions.setTitle("title");

        companyActions.create();

        assertEquals(companyActions.getValidateErrors(), null);
    }

    @Test
    public void testCreateCompanyWithoutTagLine() throws Exception {
        companyActions.setEmail(this.email);
        companyActions.setTitle("title");

        companyActions.create();

        assertEquals(companyActions.getValidateErrors().get(0).get("all"), "All fields are required");
    }

    @Test
    public void testCreateCompanyWithoutTitle() throws Exception {
        companyActions.setEmail(this.email);
        companyActions.setTagLine("tag");
        companyActions.create();

        assertEquals(companyActions.getValidateErrors().get(0).get("all"), "All fields are required");
    }

    @Test
    public void testCreateCompanyWithoutData() throws Exception {
        companyActions.setEmail(this.email);
        companyActions.create();

        assertEquals(companyActions.getValidateErrors().get(0).get("all"), "All fields are required");
    }

    @Test
    public void testCreateCompanyWithInvalidTagLine() throws Exception {
        companyActions.setEmail(this.email);
        companyActions.setTitle("title");
        companyActions.setTagLine("ThisisinvalidataglineThisisinvalidataglineThisisinvalidataglineThisisinvalidataglineThisisinvalidataglineThisisinvalidataglineThisisinvalidataglineThisisinvalidataglineThisisinvalidataglineThisisinvalidatagline");
        companyActions.create();

        assertEquals(companyActions.getValidateErrors().get(0).get("tagLine"), "TagLine length could not be more than 50 symbols");
    }

    @Test
    public void testCreateCompanyWithInvalidTitle() throws Exception {
        companyActions.setEmail(this.email);
        companyActions.setTagLine("tagline");
        companyActions.setTitle("ThisisinvalidataglineThisisinvalidataglineThisisinvalidataglineThisisinvalidataglineThisisinvalidataglineThisisinvalidataglineThisisinvalidataglineThisisinvalidataglineThisisinvalidataglineThisisinvalidatagline");
        companyActions.create();

        assertEquals(companyActions.getValidateErrors().get(0).get("title"), "Title length could not be more than 50 symbols");
    }

    @Test
    public void testCreateCompanyWithInvalidTag() throws Exception {
        companyActions.setEmail(this.email);
        companyActions.setTitle("title");
        companyActions.setTagLine("ThisisinvalidataglineThisisinvalidataglineThisisinvalidataglineThisisinvalidataglineThisisinvalidataglineThisisinvalidataglineThisisinvalidataglineThisisinvalidataglineThisisinvalidataglineThisisinvalidatagline");
        companyActions.create();

        assertEquals(companyActions.getValidateErrors().get(0).get("tagLine"), "TagLine length could not be more than 50 symbols");
    }

}
