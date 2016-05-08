package jav.comments;

import org.junit.Before;
import org.junit.Test;
import resources.comments.Comment;
import resources.comments.CommentActions;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestComment {

    private Comment comment;

    private int userId = 12;
    private Date createdOn = new Date(2016, 9, 1);
    private int projectId = 11;
    private String commentText = "nan";
    private String email = "e.shilin@paralect.com";
    public CommentActions commentActions = new CommentActions();

    @Before
    public void setUp() throws Exception {
        comment = new Comment();
        comment.setProjectId(projectId);
        comment.setCreatedOn(createdOn);
        comment.setUserId(userId);
        comment.setCommentText(commentText);
    }

    @Test
    public void getId() throws Exception {
        assertNotNull(comment.getId());
    }

    @Test
    public void getProjectId() throws Exception {
        assertEquals(comment.getProjectId(), projectId);
    }

    @Test
    public void getCreatedOn() throws Exception {
        assertEquals(comment.getCreatedOn(), createdOn);
    }

    @Test
    public void getUserId() throws Exception {
        assertEquals(comment.getUserId(), userId);
    }

    @Test
    public void getCommentText() throws Exception {
        assertEquals(comment.getCommentText(), commentText);
    }

    @Test
    public void createCommentSuccess() throws Exception {
        commentActions.setEmail(this.email);
        commentActions.setUserId(24);
        commentActions.setProjectId(26);
        commentActions.setCommentText("TEXT");

        commentActions.create();

        assertEquals(commentActions.getValidateErrors(), null);
    }

    @Test
    public void createCommentWithoutProject() throws Exception {
        commentActions.setEmail(this.email);
        commentActions.setUserId(24);

        commentActions.create();

        assertEquals(commentActions.getValidateErrors().get(0).get("all"), "Such user or company not found");
    }

    @Test
    public void createCommentWithoutUserId() throws Exception {
        commentActions.setEmail(this.email);
        commentActions.setProjectId(45);

        commentActions.create();

        assertEquals(commentActions.getValidateErrors().get(0).get("all"), "Such user or company not found"); //Such user or company not found
    }

    @Test
    public void createCommentWithUserNotExists() throws Exception {
        commentActions.setEmail(this.email);
        commentActions.setUserId(999999999);
        commentActions.setProjectId(34);

        commentActions.create();

        assertEquals(commentActions.getValidateErrors().get(0).get("user"), "This user does not exists");
    }

    @Test
    public void createCommentWithProjectNotExists() throws Exception {
        commentActions.setEmail(this.email);
        commentActions.setUserId(24);
        commentActions.setProjectId(999999999);

        commentActions.create();

        assertEquals(commentActions.getValidateErrors().get(0).get("project"), "This project does not exists");
    }

    @Test
    public void createCommentWithEmptyComment() throws Exception {
        commentActions.setEmail(this.email);
        commentActions.setUserId(24);
        commentActions.setProjectId(26);

        commentActions.create();

        assertEquals(commentActions.getValidateErrors().get(0).get("commentText"), "Comment text could not be empty");
    }

    @Test
    public void createCommentWithInvalidCommentTextx() throws Exception {
        commentActions.setEmail(this.email);
        commentActions.setUserId(24);
        commentActions.setProjectId(26);
        commentActions.setCommentText("SuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvalueSuperlongvaluev");
        commentActions.create();

        assertEquals(commentActions.getValidateErrors().get(0).get("commentText"), "Comment text could not be more than 500 chars");
    }

    @Test
    public void createCommentWithoutProjectId() throws Exception {
        commentActions.setEmail(this.email);
        commentActions.setUserId(24);

        commentActions.create();

        assertEquals(commentActions.getValidateErrors().get(0).get("all"), "Such user or company not found");
    }
}
