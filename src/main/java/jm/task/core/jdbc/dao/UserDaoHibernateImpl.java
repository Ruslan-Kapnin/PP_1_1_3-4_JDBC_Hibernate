package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.RollbackException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static final SessionFactory sessionFactory = Util.getSessionFactory();
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS Users " +
                    "(Id BIGINT not null auto_increment primary key, Name VARCHAR(20) null , " +
                    "LastName VARCHAR(20) null , Age tinyint null)").executeUpdate();
            session.getTransaction().commit();
        } catch (RollbackException e){
            try {
                if (sessionFactory.getCurrentSession().getTransaction() != null) {
                    sessionFactory.getCurrentSession().getTransaction().rollback();
                }
            } catch (HibernateException he) {
                he.printStackTrace();
            }
        } catch (IllegalStateException ise) {
            ise.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS Users").executeUpdate();
            session.getTransaction().commit();
        } catch (RollbackException e){
            try {
              if (sessionFactory.getCurrentSession().getTransaction() != null) {
                  sessionFactory.getCurrentSession().getTransaction().rollback();
              }
            } catch (HibernateException he) {
                he.printStackTrace();
            }
        } catch (IllegalStateException ise) {
            ise.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            Query query = session.createSQLQuery("insert into Users (Name, LastName, Age) values (?,?,?)");
            query.setParameter(1, name);
            query.setParameter(2, lastName);
            query.setParameter(3, age);
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (RollbackException e){
            try {
                if (sessionFactory.getCurrentSession().getTransaction() != null) {
                    sessionFactory.getCurrentSession().getTransaction().rollback();
                }
            } catch (HibernateException he) {
                he.printStackTrace();
            }
        } catch (IllegalStateException ise) {
            ise.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.remove(session.get(User.class, id));
            session.getTransaction().commit();
        } catch (RollbackException e){
            try {
                if (sessionFactory.getCurrentSession().getTransaction() != null) {
                    sessionFactory.getCurrentSession().getTransaction().rollback();
                }
            } catch (HibernateException he) {
                he.printStackTrace();
            }
        } catch (IllegalStateException ise) {
            ise.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteria = builder.createQuery(User.class);
            criteria.from(User.class);
            userList = session.createQuery(criteria).getResultList();
            session.getTransaction().commit();
        } catch (RollbackException e){
            try {
                if (sessionFactory.getCurrentSession().getTransaction() != null) {
                    sessionFactory.getCurrentSession().getTransaction().rollback();
                }
            } catch (HibernateException he) {
                he.printStackTrace();
            }
        } catch (IllegalStateException ise) {
            ise.printStackTrace();
        }

        return userList;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE TABLE Users").executeUpdate();
            session.getTransaction().commit();
        } catch (RollbackException e){
            try {
                if (sessionFactory.getCurrentSession().getTransaction() != null) {
                    sessionFactory.getCurrentSession().getTransaction().rollback();
                }
            } catch (HibernateException he) {
                he.printStackTrace();
            }
        } catch (IllegalStateException ise) {
            ise.printStackTrace();
        }
    }
}
