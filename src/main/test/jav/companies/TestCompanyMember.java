package jav.companies;

import org.junit.Before;
import org.junit.Test;
import resources.companies.CompanyMember;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by dima on 3.5.16.
 */
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
}
