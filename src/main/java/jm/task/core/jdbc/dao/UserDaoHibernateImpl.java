package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        session.createNativeQuery(
                "CREATE TABLE IF NOT EXISTS users (" +
            "id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
            "name VARCHAR(50), " +
            "last_name VARCHAR(50), " +
            "age TINYINT)"
        ).executeUpdate();

        transaction.commit();
        session.close();
        }

    @Override
    public void dropUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        session.createNativeQuery("DROP TABLE IF EXISTS users")
                .executeUpdate();

        transaction.commit();
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        User user = new User(name, lastName, age);

        session.save(user);

        transaction.commit();
        session.close();

        System.out.println("User с именем- " + name + " добавлен в базу данных");
    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        User user = session.get(User.class, id);
        if (user != null) {
            session.delete(user);
        }

        transaction.commit();
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        List<User> users = session
                .createQuery("from User", User.class)
                .list();

       transaction.commit();
       session.close();

       return users;
    }

    @Override
    public void cleanUsersTable() {
       Session session = Util.getSessionFactory().openSession();
       Transaction transaction = session.beginTransaction();

       session.createNativeQuery("DELETE FROM users")
               .executeUpdate();

       transaction.commit();
       session.close();
    }
}


