package jm.task.core.jdbc.util;


import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

public class Util {
    private final static String hostName = "localhost";
    private final static String dbName = "testdb";
    private final static String userName = "root";
    private final static String password = "alisher123";

    public static Connection getMySQLConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName;

            return DriverManager.getConnection(connectionURL, userName,
                    password);
        } catch (SQLException | ClassNotFoundException e){
            System.out.println("Ошибка при подключении к БД");
        }
        return null;
    }
    public static void closeConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName;

            DriverManager.getConnection(connectionURL, userName,
                    password).close();
        } catch (SQLException | ClassNotFoundException e){
            System.out.println("Ошибка при закрытии соединения");
        }
    }

    private static SessionFactory sessionFactory;
    public static SessionFactory getSessionFactory() {
        /*sessionFactory = new Configuration().configure("hibernate.cfg.xml")
                .addAnnotatedClass(User.class).buildSessionFactory();
        */


        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, "jdbc:mysql://" + hostName + ":3306/" + dbName +"?useSSL=false");
                settings.put(Environment.USER, userName);
                settings.put(Environment.PASS, password);
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");

                settings.put(Environment.SHOW_SQL, "true");

                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

                settings.put(Environment.HBM2DDL_AUTO, "create-drop");

                configuration.setProperties(settings);

                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return sessionFactory;
    }

}
