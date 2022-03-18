package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class Main {
    public static void main(String[] args) {
//        try {
//            Session session = factory.getCurrentSession();
//            session.beginTransaction();
//            User user = new User("name1", "sessd",(byte) 32);
//            session.save(user);
//            User user2 = new User("name2", "AAsessd",(byte) 12);
//            session.save(user2);
//            session.getTransaction().commit();
//        } finally {
//            //factory.close();
//        }

        UserServiceImpl usr = new UserServiceImpl();
        usr.createUsersTable();
        usr.saveUser("Dale", "Cooper", (byte) 32);
        usr.saveUser("Laura", "Palmer", (byte) 17);
        usr.saveUser("Harry", "Truman", (byte) 35);
        usr.saveUser("Josie", "Packard", (byte) 30);
        usr.removeUserById(1);
        List<User> list = usr.getAllUsers();
        for (User user : list) {
            System.out.println(user.toString());
        }
        usr.cleanUsersTable();
        usr.dropUsersTable();

//        Util.closeConnection();
    }
}
