package by.matsuk.shop.model.dao;

import by.matsuk.shop.entity.User;
import by.matsuk.shop.exception.DaoException;

import java.util.List;
import java.util.Optional;

/**
 * @author Ira
 * @project Postcard shop
 * The type User dao.
 */
public abstract class UserDao extends BaseDao<Long, User> {
    /**
     * Update user password boolean.
     *
     * @param password the password
     * @param login    the login
     * @return the boolean
     * @throws DaoException the dao exception
     */
    abstract public boolean updateUserPassword(String password, String login) throws DaoException;

    /**
     * Find user by login optional.
     *
     * @param login the login
     * @return the optional
     * @throws DaoException the dao exception
     */
    abstract public Optional<User> findUserByLogin(String login) throws DaoException;

    /**
     * Find user by last name optional.
     *
     * @param lastName the last name
     * @return the optional
     * @throws DaoException the dao exception
     */
    abstract public Optional<User> findUserByLastName(String lastName) throws DaoException;

    /**
     * Find user by email optional.
     *
     * @param email the email
     * @return the optional
     * @throws DaoException the dao exception
     */
    abstract public Optional<User> findUserByEmail(String email) throws DaoException;

    /**
     * Find users by last name list.
     *
     * @param user               the user
     * @param startElementNumber the start element number
     * @return the list
     * @throws DaoException the dao exception
     */
    abstract public List<User> findUsersByLastName(User user, int startElementNumber) throws DaoException;

    /**
     * Find users by status list.
     *
     * @param user               the user
     * @param startElementNumber the start element number
     * @return the list
     * @throws DaoException the dao exception
     */
    abstract public List<User> findUsersByStatus(User user, int startElementNumber) throws DaoException;

    /**
     * Find users by role list.
     *
     * @param user               the user
     * @param startElementNumber the start element number
     * @return the list
     * @throws DaoException the dao exception
     */
    abstract public List<User> findUsersByRole(User user, int startElementNumber) throws DaoException;

    /**
     * Update user login boolean.
     *
     * @param currentLogin the current login
     * @param newLogin     the new login
     * @return the boolean
     * @throws DaoException the dao exception
     */
    abstract public boolean updateUserLogin(String currentLogin, String newLogin) throws DaoException;

    /**
     * Update user status boolean.
     *
     * @param login         the login
     * @param currentStatus the current status
     * @return the boolean
     * @throws DaoException the dao exception
     */
    abstract public boolean updateUserStatus(String login, String currentStatus) throws DaoException;

    /**
     * Update user role boolean.
     *
     * @param login       the login
     * @param currentRole the current role
     * @return the boolean
     * @throws DaoException the dao exception
     */
    abstract public boolean updateUserRole(String login, String currentRole) throws DaoException;
}
