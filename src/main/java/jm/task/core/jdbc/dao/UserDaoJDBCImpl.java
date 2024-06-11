package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoJDBCImpl implements UserDao {

    private static Logger LOGGER = Logger.getLogger("DAO LOGGER");
    private Connection connection = Util.createConnection();
    private static final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS user (" +
            "id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
            "name VARCHAR(50), " +
            "lastname VARCHAR(50), " +
            "age TINYINT);";

    private static final String DROP_TABLE_SQL = "DROP TABLE IF EXISTS user;";
    private static final String SAVE_SQL = "INSERT INTO user (name, lastname, age) VALUES (?, ?, ?);";
    private static final String REMOVE_BY_ID_SQL = "DELETE FROM user WHERE id = ?;";
    private static final String SELECT_ALL_SQL = "SELECT id, name, lastname, age FROM user;";
    private static final String CLEAN_TABLE_SQL = "TRUNCATE TABLE user;";

    public UserDaoJDBCImpl() {
        // Конструктор класса без параметров
    }

    public void createUsersTable() {
        try (PreparedStatement preparedStatement = connection.prepareStatement(CREATE_TABLE_SQL)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error creating table: " + e.getMessage());
        }
    }

    public void dropUsersTable() {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DROP_TABLE_SQL)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error dropping table: " + e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error saving user: " + e.getMessage());
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error removing user by ID: " + e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_SQL)) {
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                User user = new User(
                        result.getString("name"),
                        result.getString("lastname"),
                        result.getByte("age"));
                user.setId(result.getLong("id"));
                list.add(user);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting all users: " + e.getMessage());
        }
        return list;
    }

    public void cleanUsersTable() {
        try (PreparedStatement preparedStatement = connection.prepareStatement(CLEAN_TABLE_SQL)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error cleaning users table: " + e.getMessage());
        }
    }
}

