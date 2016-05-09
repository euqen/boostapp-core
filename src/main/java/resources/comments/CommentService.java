package resources.comments;

import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import resources.comments.Comment;
import resources.companies.Company;
import resources.infrastructure.SessionHelper;

public class CommentService {
    @Resource(name="sessionFactory")
    private static SessionFactory sessionFactory = SessionHelper.sessionFactory;

    public static Comment getById(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Comment.class, id);
    }

    public static List<Comment> getAll() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM Comment");
        List<Comment> comments = query.list();
        session.getTransaction().commit();

        return comments;
    }

    public static List<Comment> getAllByProjectId(Integer projectId) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM Comment WHERE projectId = " + projectId);
        List<Comment> comments = query.list();
        session.getTransaction().commit();

        return comments;
    }

    public static Comment save(Comment comment) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.save(comment);
        session.getTransaction().commit();

        return comment;
    }

    public static void delete(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Comment comment = (Comment) session.get(Comment.class, id);
        session.delete(comment);
        session.getTransaction().commit();
    }

    public static void update(Comment comment) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Comment existingComment = (Comment) session.get(Comment.class, comment.getId());

        existingComment.setCommentText(comment.getCommentText());

        session.save(existingComment);
        session.getTransaction().commit();
    }
}