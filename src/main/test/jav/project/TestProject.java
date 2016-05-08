package jav.project;

import org.junit.Before;
import org.junit.Test;
import resources.projects.Project;
import resources.projects.ProjectsAction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestProject {
    private Project project;

    private String title = "Murder";
    private String description = "ACTIVE";
    private String email = "e.shilin@paralect.com";
    private Integer amount = 55;
    private Integer paymentAmount = 12;
    private int companyId = 11;
    private ProjectsAction projectsAction = new ProjectsAction();


    @Before
    public void setUp() throws Exception {
        project = new Project();
        project.setCompanyId(companyId);
        project.setTitle(title);
        project.setAmount(amount);
        project.setDescription(description);
        project.setPaymentAmount(paymentAmount);
    }

    @Test
    public void getId() throws Exception {
        assertNotNull(project.getId());
    }

    @Test
    public void getCompanyId() throws Exception {
        assertEquals(project.getCompanyId(), companyId);
    }

    @Test
    public void getTitle() throws Exception {
        assertEquals(project.getTitle(), title);
    }

    @Test
    public void getAmount() throws Exception {
        assertEquals(project.getAmount(), amount);
    }

    @Test
    public void getDescription() throws Exception {
        assertEquals(project.getDescription(), description);
    }

    @Test
    public void getPaymentAmount() throws Exception {
        assertEquals(project.getPaymentAmount(), paymentAmount);
    }

    @Test
    public void getTitles() throws Exception {
        assertEquals(project.getTitle(), title);
    }

    @Test
    public void testCreateProjectNotLoggedIn() throws Exception {
        projectsAction.create();

        assertEquals(projectsAction.getValidateErrors().get(0).get("user"), "You are not logged in");
    }

    @Test
    public void testCreateProjectWithoutData() throws Exception {
        projectsAction.setEmail(this.email);
        projectsAction.create();

        assertEquals(projectsAction.getValidateErrors().get(0).get("all"), "All fields are required");
    }

    @Test
    public void testCreateProjectWithTitle() throws Exception {
        projectsAction.setEmail(this.email);
        projectsAction.setTitle("asfasfas");
        projectsAction.create();

        assertEquals(projectsAction.getValidateErrors().get(0).get("all"), "All fields are required");
    }

    @Test
    public void testCreateProjectWithTitleAndDescription() throws Exception {
        projectsAction.setEmail(this.email);
        projectsAction.setTitle("asfasfas");
        projectsAction.setDescription("afsafs");
        projectsAction.create();

        assertEquals(projectsAction.getValidateErrors().get(0).get("all"), "All fields are required");
    }

    @Test
    public void testCreateProjectWithTitleAndDescriptionLenghZero() throws Exception {
        projectsAction.setEmail(this.email);
        projectsAction.setTitle("");
        projectsAction.setDescription("");
        projectsAction.create();

        assertEquals(projectsAction.getValidateErrors().get(0).get("all"), "All fields are required");
    }

    @Test
    public void testCreateProjectWithIncorrectAmount() throws Exception {
        projectsAction.setEmail(this.email);
        projectsAction.setTitle("sgdgsdgds");
        projectsAction.setDescription("gsdsgdsdg");
        projectsAction.setAmount(100000000);
        projectsAction.create();

        assertEquals(projectsAction.getValidateErrors().get(0).get("amount"), "amount could not be more than 10000000");
    }

    @Test
    public void testCreateProjectWithIncorrectTitle() throws Exception {
        projectsAction.setEmail(this.email);
        projectsAction.setTitle("IncorrectTitleIncorrectTitleIncorrectTitleIncorrectTitleIncorrectTitleIncorrectTitleIncorrectTitleIncorrectTitleIncorrectTitleIncorrectTitleIncorrectTitleIncorrectTitleIncorrectTitleIncorrectTitleIncorrectTitleIncorrectTitle");
        projectsAction.setDescription("gsdsgdsdg");
        projectsAction.setAmount(35345);
        projectsAction.create();

        assertEquals(projectsAction.getValidateErrors().get(0).get("title"), "title could not be more than 100 symbols");
    }

    @Test
    public void testCreateProjectWithIncorrectDescription() throws Exception {
        projectsAction.setEmail(this.email);
        projectsAction.setTitle("sdfsdf");
        projectsAction.setDescription("IncorrectTitleIncorrectTitleIncorrectTitleIncorrectTitleIncorrectTitleIncorrectTitleIncorrectTitleIncorrectTitleIncorrectTitleIncorrectTitleIncorrectTitleIncorrectTitleIncorrectTitleIncorrectTitleIncorrectTitleIncorrectTitleIncorrectTitleIncorrectTitleIncorrectTitleIncorrectTitleIncorrectTitleIncorrectTitleIncorrectTitleIncorrectTitleIncorrectTitleIncorrectTitleIncorrectTitleIncorrectTitleIncorrectTitleIncorrectTitleIncorrectTitleIncorrectTitle");
        projectsAction.setAmount(35345);
        projectsAction.create();

        assertEquals(projectsAction.getValidateErrors().get(0).get("desc"), "desc could not be more than 500 symbols");
    }

    @Test
    public void testCreateProjectWithoutCompany() throws Exception {
        projectsAction.setEmail(this.email);
        projectsAction.setTitle("sdfsdf");
        projectsAction.setDescription("fsdfsdf");
        projectsAction.setAmount(35345);
        projectsAction.create();

        assertEquals(projectsAction.getValidateErrors().get(0).get("company"), "Please select company");
    }

    @Test
    public void testCreateProjectWithNotLoggedIn() throws Exception {
        projectsAction.setRemove(true);

        projectsAction.update();

        assertEquals(projectsAction.getValidateErrors().get(0).get("user"), "You are not logged in");


    }

    @Test
    public void testCreateProjectWithoutProjectId() throws Exception {
        projectsAction.setRemove(true);
        projectsAction.setEmail(this.email);

        projectsAction.update();

        assertEquals(projectsAction.getValidateErrors().get(0).get("project"), "Such project does not exists");


    }

    @Test
    public void testCreateProjectWithIncorrectProjectId() throws Exception {
        projectsAction.setRemove(true);
        projectsAction.setEmail(this.email);
        projectsAction.setId(-11111);

        projectsAction.update();

        assertEquals(projectsAction.getValidateErrors().get(0).get("project"), "Such project does not exists");
    }

    @Test
    public void testUpdateProjectWithoutUser() throws Exception {
        projectsAction.update();

        assertEquals(projectsAction.getValidateErrors().get(0).get("user"), "You are not logged in");
    }

    @Test
    public void testUpdateProjectWithoutData() throws Exception {
        projectsAction.setEmail(this.email);
        projectsAction.update();

        assertEquals(projectsAction.getValidateErrors().get(0).get("all"), "All fields are required");
    }

    @Test
    public void testCreateProjectNotLogged() throws Exception {
        projectsAction.create();

        assertEquals(projectsAction.getValidateErrors().get(0).get("user"), "You are not logged in");
    }
}
