package by.matsuk.shop.model.dao;

import by.matsuk.shop.entity.AbstractEntity;
import by.matsuk.shop.exception.DaoException;
import by.matsuk.shop.model.connection.ConnectionPool;
import by.matsuk.shop.model.connection.ProxyConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

/**
 * @author Ira
 * @project Postcard shop
 * The type AbstractDao.
 *
 * @param <T> the type parameter
 */
public abstract class AbstractDao<T extends AbstractEntity> {
    protected static final Logger LOGGER = LogManager.getLogger();
    /**
     * The Connection.
     */
    protected ProxyConnection proxyConnection;

    /**
     * Add long.
     *
     * @param t the t
     * @return the long
     * @throws DaoException the dao exception
     */
    public abstract boolean create(T t) throws DaoException;

    /**
     * Update boolean.
     *
     * @param t the t
     * @return the boolean
     * @throws DaoException the dao exception
     */
    public abstract Optional<T> update(T t) throws DaoException;

    /**
     * Delete boolean.
     *
     * @param id the id
     * @return the boolean
     * @throws DaoException the dao exception
     */
    public abstract boolean delete(long id) throws DaoException;

    /**
     * Find all list.
     *
     * @return the list
     * @throws DaoException the dao exception
     */
    public abstract List<T> findAll() throws DaoException;

    /**
     * Find by id optional.
     *
     * @param id the id
     * @return the optional
     * @throws DaoException the dao exception
     */
    public abstract Optional<T> findById(long id) throws DaoException;

    /**
     * Sets connection.
     *
     * @param connection the connection
     */
    public void setConnection(Connection connection) {
        this.proxyConnection = (ProxyConnection) connection;
    }

    /**
     * Close connection.
     */
    public void closeConnection() {
        if (proxyConnection != null) {
            ConnectionPool.getInstance().releaseConnection(proxyConnection);
        }
    }
}
