package jav.invites;

import org.junit.Before;
import org.junit.Test;
import resources.invites.Invite;
import resources.invites.InviteActions;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestInvite {
    private Invite invite;

    private int userId = 12;
    private Date createdOn = new Date(2016, 9, 1);
    private int companyId = 11;
    private String email = "e.shilin@paralect.com";
    private String status = "Active";
    private InviteActions inviteActions = new InviteActions();


    @Before
    public void setUp() throws Exception {
        invite = new Invite();
        invite.setCompanyId(companyId);
        invite.setCreatedOn(createdOn);
        invite.setUserId(userId);
        invite.setStatus(status);
    }

    @Test
    public void getId() throws Exception {
        assertNotNull(invite.getId());
    }

    @Test
    public void getCompanyId() throws Exception {
        assertEquals(invite.getCompanyId(), companyId);
    }

    @Test
    public void getCreatedOn() throws Exception {
        assertEquals(invite.getCreatedOn(), createdOn);
    }

    @Test
    public void getUserId() throws Exception {
        assertEquals(invite.getUserId(), userId);
    }

    @Test
    public void getStatus() throws Exception {
        assertEquals(invite.getStatus(), status);
    }

    @Test
    public void testCreateInviteWithoutData() throws Exception {
        inviteActions.setEmail(this.email);
        inviteActions.create();
        assertEquals(inviteActions.getValidateErrors().get(0).get("all"), "Please specify user and company");
    }

    @Test
    public void testCreateInviteNotLoggedIn() throws Exception {
        inviteActions.create();
        assertEquals(inviteActions.getValidateErrors().get(0).get("user"), "You are not logged in");
    }

    @Test
    public void testCreateInviteWithoutUserId() throws Exception {
        inviteActions.setEmail(this.email);
        inviteActions.setCompanyId(123);
        inviteActions.create();

        assertEquals(inviteActions.getValidateErrors().get(0).get("all"), "Please specify user and company");
    }

    @Test
    public void testCreateInviteWithoutCompanyId() throws Exception {
        inviteActions.setEmail(this.email);
        inviteActions.setUserId(123);
        inviteActions.create();

        assertEquals(inviteActions.getValidateErrors().get(0).get("all"), "Please specify user and company");
    }

    @Test
    public void testCreateInviteWithUserWhichNotExists() throws Exception {
        inviteActions.setEmail(this.email);
        inviteActions.setUserId(123234);
        inviteActions.setCompanyId(123243);
        inviteActions.create();

        assertEquals(inviteActions.getValidateErrors().get(0).get("user"), "this user does not exists");
    }

    @Test
    public void testCreateInviteWithCompanyWhichNotExists() throws Exception {
        inviteActions.setEmail(this.email);
        inviteActions.setUserId(21);
        inviteActions.setCompanyId(123243);
        inviteActions.create();

        assertEquals(inviteActions.getValidateErrors().get(1).get("company"), "this company does not exists");
    }

    @Test
    public void testCreateInviteWithIncorrectLoggedUser() throws Exception {
        inviteActions.setEmail("parapapar@papdas.ru");
        inviteActions.setUserId(21);
        inviteActions.setCompanyId(123243);
        inviteActions.create();

        assertEquals(inviteActions.getValidateErrors().get(0).get("user"), "You are not logged in");
    }

    @Test
    public void testCreateInviteWithoutUser() throws Exception {
        inviteActions.setEmail(this.email);
        inviteActions.setCompanyId(123);
        inviteActions.create();

        assertEquals(inviteActions.getValidateErrors().get(0).get("all"), "Please specify user and company");
    }
}
