package by.matsuk.shop.model.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;


/**
 * @project Postcard shop "Card4You"
 * @author Ira
 * The type Connection factory
 */
class ConnectionFactory {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String DATABASE_PROPERTY_FILE = "database";
    private static final String DATABASE_URL_PROPERTY = "url";
    private static final String DATABASE_DRIVER_PROPERTY = "driver";
    private static final String DATABASE_USER_PROPERTY = "user";
    private static final String DATABASE_PASSWORD_PROPERTY = "password";
    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;

    static {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(DATABASE_PROPERTY_FILE);
        String driver;
        if (resourceBundle.containsKey(DATABASE_DRIVER_PROPERTY)) {
            driver = resourceBundle.getString(DATABASE_DRIVER_PROPERTY);
        } else {
            LOGGER.fatal("Error getting value of driver property");
            throw new RuntimeException("Error getting value of driver property");
        }
        if (resourceBundle.containsKey(DATABASE_URL_PROPERTY)) {
            URL = resourceBundle.getString(DATABASE_URL_PROPERTY);
        } else {
            LOGGER.fatal("Error getting value of url property");
            throw new RuntimeException("Error getting value of url property");
        }
        if (resourceBundle.containsKey(DATABASE_USER_PROPERTY)) {
            USER = resourceBundle.getString(DATABASE_USER_PROPERTY);
        } else {
            LOGGER.fatal("Error getting value of user property");
            throw new RuntimeException("Error getting value of user property");
        }
        if (resourceBundle.containsKey(DATABASE_PASSWORD_PROPERTY)) {
            PASSWORD = resourceBundle.getString(DATABASE_PASSWORD_PROPERTY);
        } else {
            LOGGER.fatal("Error getting value of password property");
            throw new RuntimeException("Error getting value of password property");
        }
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            LOGGER.fatal("Driver {} was not found: {}", driver, e);
            throw new RuntimeException("Driver " + driver + "was not fount: ", e);
        }
    }

    private ConnectionFactory() {
    }

    /**
     * Get connection
     *
     * @return the connection
     * @throws SQLException the sql exception
     */
    static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
