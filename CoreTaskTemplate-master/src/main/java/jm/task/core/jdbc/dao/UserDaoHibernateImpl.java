package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;

import java.util.List;

import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {
    private final String table = "new_table";

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Session session = null;
        try {
            session = getSessionFactory().openSession();
            String str = "CREATE TABLE `testdb`.`" + table + "` ( " +
                    " `id` INT NOT NULL AUTO_INCREMENT, " +
                    " `name` VARCHAR(45) NULL, " +
                    " `lastName` VARCHAR(45) NULL, " +
                    " `age` INT(3) NULL, " +
                    " PRIMARY KEY (`id`));";

            session.createSQLQuery(str).addEntity(User.class).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            assert session != null;
            session.close();
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = null;
        try {
            session = getSessionFactory().getCurrentSession();
            session.beginTransaction();
            String str = "DROP TABLE " + table + ";";
            session.createSQLQuery(str).addEntity(User.class).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            assert session != null;
            session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = null;
        try {
            session = getSessionFactory().openSession();
            session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            assert session != null;
            session.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = null;
        try {
            session = getSessionFactory().openSession();
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            assert session != null;
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session = null;
        try {
            session = getSessionFactory().openSession();
            session.beginTransaction();
            String str = "from " + User.class.getSimpleName();

            List<User> users = session.createQuery(str).getResultList();
            session.getTransaction().commit();
            return users;
        } catch (Exception e) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            assert session != null;
            session.close();
        }
        return null;
    }

    @Override
    public void cleanUsersTable() {
        Session session = null;
        try {
            session = getSessionFactory().openSession();
            session.beginTransaction();
            String str = "DELETE FROM " + User.class.getSimpleName();
            session.createQuery(str).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            assert session != null;
            session.close();
        }
    }
}
