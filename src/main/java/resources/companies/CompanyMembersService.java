package resources.companies;

import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import resources.comments.Comment;
import resources.companies.Company;
import resources.infrastructure.SessionHelper;

public class CompanyMembersService {

    @Resource(name="sessionFactory")
    private static SessionFactory sessionFactory = SessionHelper.sessionFactory;

    public static CompanyMember getById(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(CompanyMember.class, id);
    }

    public static List<CompanyMember> getByUserId(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM CompanyMember WHERE userId = " + id);
        List<CompanyMember> cm = query.list();
        session.getTransaction().commit();

        return cm;
    }

    public static List<CompanyMember> getByCompanyId(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM CompanyMember WHERE companyId = " + id);
        List<CompanyMember> cm = query.list();
        session.getTransaction().commit();

        return cm;
    }

    public static CompanyMember getUserInCompany(Integer userId, Integer companyId) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM CompanyMember WHERE userId = " + userId + " AND companyId = " + companyId);
        List<CompanyMember> cm = query.list();
        session.getTransaction().commit();

        return cm.isEmpty() ? null : cm.get(0);
    }

    public static void save(CompanyMember companyMember) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.save(companyMember);
        session.getTransaction().commit();
    }

    public static void delete(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        CompanyMember companyMember = (CompanyMember) session.get(CompanyMember.class, id);
        session.delete(companyMember);
        session.getTransaction().commit();
    }

    public static void update(CompanyMember companyMember) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        CompanyMember existingCompanyMember = (CompanyMember) session.get(CompanyMember.class, companyMember.getId());

        existingCompanyMember.setCompanyId(companyMember.getCompanyId());
        existingCompanyMember.setUserId(companyMember.getUserId());

        session.save(existingCompanyMember);
        session.getTransaction().commit();
    }
}