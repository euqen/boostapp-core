package resources.payments;

import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import resources.payments.Payment;
import resources.companies.Company;
import resources.infrastructure.SessionHelper;
import resources.projects.Project;

public class PaymentService {
    @Resource(name="sessionFactory")
    private static SessionFactory sessionFactory = SessionHelper.sessionFactory;

    public static Payment getById(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Payment p = session.get(Payment.class, id);
        session.getTransaction().commit();
        return p;
    }

    public static List<Payment> getAllByProjectId(Integer projectId) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM Payment WHERE projectId = " + projectId);
        List<Payment> payments = query.list();
        session.getTransaction().commit();

        return payments;
    }

    public static List<Payment> getAllByUserId(Integer userId) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM Payment WHERE userId = " + userId);
        List<Payment> payments = query.list();
        session.getTransaction().commit();

        return payments;
    }

    public static List<Payment> getAll() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM Payment");
        List<Payment> payments = query.list();
        session.getTransaction().commit();

        return payments;
    }

    public static Payment save(Payment comment) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.save(comment);
        session.getTransaction().commit();

        return comment;
    }

    public static void delete(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Payment comment = (Payment) session.get(Payment.class, id);
        session.delete(comment);
        session.getTransaction().commit();
    }
}