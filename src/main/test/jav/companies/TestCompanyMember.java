package jav.companies;

import org.junit.Before;
import org.junit.Test;
import resources.companies.CompanyMember;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class TestCompanyMember {

    private CompanyMember companyMember;

    private Integer companyId = 17;
    private Integer userId = 12;
    private String role = "User";


    @Before
    public void setUp() throws Exception {
        companyMember = new CompanyMember();
        companyMember.setCompanyId(companyId);
        companyMember.setUserId(userId);
        companyMember.setRole(role);
    }

    @Test
    public void getId() throws Exception {
        assertNotNull(companyMember.getId());
    }

    @Test
    public void getCompanyId() throws Exception {
        assertEquals(companyMember.getCompanyId(), companyId);
    }

    @Test
    public void getUserId() throws Exception {
        assertEquals(companyMember.getUserId(), userId);
    }

    @Test
    public void getRole() throws Exception {
        assertEquals(companyMember.getRole(), role);
    }

    @Test
    public void getCMId() throws Exception {
        assertNotNull(companyMember.getId());
    }

    @Test
    public void getCMCompanyId() throws Exception {
        assertEquals(companyMember.getCompanyId(), companyId);
    }

    @Test
    public void getCMUserId() throws Exception {
        assertEquals(companyMember.getUserId(), userId);
    }

    @Test
    public void getCmRole() throws Exception {
        assertEquals(companyMember.getRole(), role);
    }
}
