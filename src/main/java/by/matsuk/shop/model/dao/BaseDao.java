package by.matsuk.shop.model.dao;

import by.matsuk.shop.entity.AbstractEntity;
import by.matsuk.shop.exception.DaoException;
import by.matsuk.shop.model.connection.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

/**
 * @author Ira
 * @project Postcard shop
 * The type Base dao.
 *
 * @param <K> the type parameter
 * @param <T> the type parameter
 */
public abstract class BaseDao<K, T extends AbstractEntity> {
    protected static final Logger LOGGER = LogManager.getLogger();
    /**
     * The Connection.
     */
    protected Connection connection;

    /**
     * Add long.
     *
     * @param t the t
     * @return the long
     * @throws DaoException the dao exception
     */
    abstract public long add(T t) throws DaoException;

    /**
     * Update boolean.
     *
     * @param t the t
     * @return the boolean
     * @throws DaoException the dao exception
     */
    abstract public boolean update(T t) throws DaoException;

    /**
     * Delete boolean.
     *
     * @param k the k
     * @return the boolean
     * @throws DaoException the dao exception
     */
    abstract public boolean delete(K k) throws DaoException;

    /**
     * Find all list.
     *
     * @return the list
     * @throws DaoException the dao exception
     */
    abstract public List<T> findAll() throws DaoException;

    /**
     * Find by id optional.
     *
     * @param k the k
     * @return the optional
     * @throws DaoException the dao exception
     */
    abstract public Optional<T> findById(K k) throws DaoException;

    /**
     * Sets connection.
     *
     * @param connection the connection
     */
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     * Close connection.
     */
    public void closeConnection() {
        if (connection != null) {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
    }
}
