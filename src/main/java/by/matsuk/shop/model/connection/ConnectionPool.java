package by.matsuk.shop.model.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The type Connection pool.
 *
 * @author Ira
 * @project Postcard shop
 */
public class ConnectionPool {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String POOL_PROPERTY_FILE = "pool";
    private static final String POOL_SIZE_PROPERTY = "size";
    private static final int DEFAULT_POOL_SIZE = 8;
    private static final int POOL_SIZE;
    private static final AtomicBoolean isCreated = new AtomicBoolean(false);
    private static final Lock creationLock = new ReentrantLock(true);
    private static BlockingQueue<ProxyConnection> freeConnections; // FIXME or BlockingQueue? static or non-static?
    private static BlockingQueue<ProxyConnection> takenConnections;
    private static ConnectionPool instance;

    static { // FIXME wrap into try?
        ResourceBundle resourceBundle = ResourceBundle.getBundle(POOL_PROPERTY_FILE);
        String poolSize;
        if (resourceBundle.containsKey(POOL_SIZE_PROPERTY)) {
            poolSize = resourceBundle.getString(POOL_SIZE_PROPERTY);
            POOL_SIZE = Integer.parseInt(poolSize);
        } else {
            LOGGER.warn("Error getting pool size value: pool size will be initialized with default value.");
            POOL_SIZE = DEFAULT_POOL_SIZE;
        }
    }

    private ConnectionPool() {
        freeConnections = new LinkedBlockingQueue<>(POOL_SIZE);
        takenConnections = new LinkedBlockingQueue<>(POOL_SIZE);
        for (int i = 0; i < POOL_SIZE; i++) {
            try {
                Connection connection = ConnectionFactory.getConnection();
                ProxyConnection proxyConnection = new ProxyConnection(connection);
                freeConnections.add(proxyConnection);
            } catch (SQLException e) {
                LOGGER.error("An error occurred while creating the connection: " + e);
            }
        }
        if (freeConnections.isEmpty()) {
            LOGGER.fatal("Error: no connections were created");
            throw new RuntimeException("Error: no connections were created");
        }
        LOGGER.info("{} connections were created", freeConnections.size());
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static ConnectionPool getInstance() {
        if (!isCreated.get()) {
            try {
                creationLock.lock();
                if (isCreated.compareAndSet(false, true)) {
                    instance = new ConnectionPool();
                }
            } finally {
                creationLock.unlock();
            }
        }
        return instance;
    }

    /**
     * Gets connection.
     *
     * @return the connection
     */
    public Connection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = freeConnections.take();
            takenConnections.put(connection);
        } catch (InterruptedException e) {
            LOGGER.error("An error occurred while getting the connection: " + e);
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    /**
     * Release connection boolean.
     *
     * @param connection the connection
     * @return the boolean
     */
    public boolean releaseConnection(Connection connection) {
        if (!(connection instanceof ProxyConnection)) {
            return false;
        }
        try {
            if (takenConnections.remove(connection)) {
                freeConnections.put((ProxyConnection) connection);
                return true;
            }
        } catch (InterruptedException e) {
            LOGGER.error("An error occurred while releasing the connection: " + e);
            Thread.currentThread().interrupt();
        }
        return false;
    }

    /**
     * Destroy pool.
     */
    public void destroyPool() {
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            try {
                freeConnections.take().reallyClose();
            } catch (InterruptedException | SQLException e) {
                LOGGER.error("An error occurred while destroying the pool: " + e);
                Thread.currentThread().interrupt();
            }
            i++; // FIXME
        }
        deregisterDrivers();
    }

    private void deregisterDrivers() {
        DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                LOGGER.error("An error occurred while deregister drivers: " + e);
                Thread.currentThread().interrupt();
            }
        });
    }
}
