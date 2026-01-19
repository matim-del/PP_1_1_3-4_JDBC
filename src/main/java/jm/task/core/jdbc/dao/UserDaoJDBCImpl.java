package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String sql = """
        CREATE TABLE IF NOT EXISTS users ( 
            id BIGINT PRIMARY KEY AUTO_INCREMENT,
            name VARCHAR(50),
            last_name VARCHAR(50),
            age TINYINT
        )
    """;

            try (Connection connection = Util.getConnection();
                 Statement statement = connection.createStatement()) {

                statement.executeUpdate(sql);

            } catch (Exception e) {
                throw new RuntimeException("Ошибка при создании таблицы users", e);
            }
    }

    public void dropUsersTable() {
            String sql = "DROP TABLE IF EXISTS users";

            try (Connection connection = Util.getConnection();
                 Statement statement = connection.createStatement()) {

                statement.executeUpdate(sql);

            } catch (Exception e) {
                throw new RuntimeException("Ошибка при удалении таблицы users", e);
            }
        }

    public void saveUser(String name, String lastName, byte age) {
            String sql = "INSERT INTO users (name, last_name, age) VALUES (?, ?, ?)";

            try (Connection connection = Util.getConnection();
                 PreparedStatement ps = connection.prepareStatement(sql)) {

                ps.setString(1, name);
                ps.setString(2, lastName);
                ps.setByte(3, age);

                ps.executeUpdate();

                System.out.println("User с именем – " + name + " добавлен в базу данных");

            } catch (Exception e) {
                throw new RuntimeException("Ошибка при добавлении пользователя", e);
            }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection connection = Util.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();

        } catch (Exception e) {
           throw new RuntimeException("Ошибка при удалении пользователя", e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try {Connection connection = Util.getConnection();
            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                String lastName = rs.getString("last_name");
                Byte age = rs.getByte("age");

                User user = new User();
                user.setId(id);
                user.setName(name);
                user.setLastName(lastName);
                user.setAge(age);

                users.add(user);
            }
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при получении пользователя", e);
        }
        return users;
    }

    public void cleanUsersTable() {
        String sql = "TRUNCATE TABLE users";
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(sql);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при очищения таблицы");
        }
    }
}
