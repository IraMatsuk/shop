package by.matsuk.shop.model.dao.impl;

import by.matsuk.shop.entity.User;
import by.matsuk.shop.exception.DaoException;
import by.matsuk.shop.model.connection.ConnectionPool;
import by.matsuk.shop.model.dao.UserDao;

import java.sql.*;
import java.util.List;
import java.util.Optional;

/**
 * @author Ira
 * @project Postcard shop
 * The type User dao.
 */
public class UserDaoImpl extends UserDao {
    private static final String SQL_INSERT_USER = "INSERT INTO users(first_name, last_name, login, password, email, phone, address, registration_date, user_role) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_USER = "UPDATE users SET first_name = ?, last_name = ?, email = ?, phone = ?, address = ? WHERE login = ?";
    private static final String SQL_UPDATE_USER_PASSWORD = "UPDATE users SET password = ? WHERE login = ?";
    private static final String SQL_UPDATE_USER_LOGIN = "UPDATE users SET login = ? WHERE login = ?";
    private static final String SQL_UPDATE_USER_STATUS = "UPDATE users SET user_status = ? WHERE login = ?";
    private static final String SQL_UPDATE_USER_ROLE = "UPDATE users SET user_role = ? WHERE login = ?";
    private static final String SQL_DELETE_USER = "DELETE FROM users WHERE login = ?";
    private static final String SQL_SELECT_ALL_USERS = "SELECT user_id, first_name, last_name, login, password, email, phone, address, user_role, user_status FROM users";
    private static final String SQL_SELECT_USERS_BY_LOGIN = "SELECT user_id, first_name, last_name, login, password, email, phone, address, user_role, user_status FROM users WHERE login= ?";
    private static final String SQL_SELECT_USER_PASSWORD = "SELECT password FROM users WHERE login= ?";
    private static final String SQL_SELECT_USERS_BY_EMAIL = "SELECT user_id, first_name, last_name, login, password, email, phone, address, user_role, user_status FROM users WHERE email= ?";
    private static final String SQL_SELECT_USERS_BY_LASTNAME = "SELECT user_id, first_name, last_name, login, password, email, phone, address, user_role, user_status FROM users" +
            "WHERE last_name LIKE ? AND role = ? LIMIT ?, 15";
    private static final String SQL_SELECT_USERS_BY_FULL_NAME = "SELECT user_id, first_name, last_name, login, password, email, phone, address, user_role, user_status FROM users" +
            "WHERE first_name LIKE ? AND last_name LIKE ? AND role ? LIMIT ?, 15";
    private static final String SQL_SELECT_USERS_BY_STATUS = "SELECT user_id, first_name, last_name, login, password, email, phone, address, user_role, user_status FROM users" +
            "WHERE user_status = ? AND role ? LIMIT ?, 15";
    private static final String SQL_SELECT_ALL_USERS_BY_ROLE = "SELECT user_id, first_name, last_name, login, password, email, phone, address, user_role, user_status FROM users WHERE user_role = ?";
    private static final String SQL_SELECT_USERS_BY_ROLE = "SELECT user_id, first_name, last_name, login, password, email, phone, address, user_role, user_status FROM users WHERE user_role = ? LIMIT ?, 15";

    /**
     * Instantiates a new User dao.
     */
    public UserDaoImpl() {
    }

    /**
     * Instantiates a new User dao.
     *
     * @param isTransaction the is transaction
     */
    public UserDaoImpl(boolean isTransaction) {
        if (!isTransaction) {
            connection = ConnectionPool.getInstance().getConnection();
        }
    }


    @Override
    public long add(User user) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getLogin());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setString(6, user.getPhone());
           // preparedStatement.setString(7, user.getAddress());
            preparedStatement.setTimestamp(7, java.sql.Timestamp.from(java.time.Instant.now()));
            preparedStatement.setString(8, user.getUserRole().getRole());
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            return resultSet.getLong(1);
        } catch (SQLException e) {
            LOGGER.error("Error while adding user: " + e);
            throw new DaoException("Error while adding user: ", e);
        }
    }

    @Override
    public boolean update(User user) throws DaoException {
        return false;
    }

    @Override
    public boolean delete(Long aLong) throws DaoException {
        return false;
    }

    @Override
    public List<User> findAll() throws DaoException {
        return null;
    }

    @Override
    public Optional<User> findById(Long aLong) throws DaoException {
        return Optional.empty();
    }

    @Override
    public boolean updateUserPassword(String password, String login) throws DaoException {
        return false;
    }

    @Override
    public Optional<User> findUserByLogin(String login) throws DaoException {
        return Optional.empty();
    }

    @Override
    public Optional<User> findUserByLastName(String lastName) throws DaoException {
        return Optional.empty();
    }

    @Override
    public Optional<User> findUserByEmail(String email) throws DaoException {
        return Optional.empty();
    }

    @Override
    public List<User> findUsersByLastName(User user, int startElementNumber) throws DaoException {
        return null;
    }

    @Override
    public List<User> findUsersByStatus(User user, int startElementNumber) throws DaoException {
        return null;
    }

    @Override
    public List<User> findUsersByRole(User user, int startElementNumber) throws DaoException {
        return null;
    }

    @Override
    public boolean updateUserLogin(String currentLogin, String newLogin) throws DaoException {
        return false;
    }

    @Override
    public boolean updateUserStatus(String login, String currentStatus) throws DaoException {
        return false;
    }

    @Override
    public boolean updateUserRole(String login, String currentRole) throws DaoException {
        return false;
    }
}
