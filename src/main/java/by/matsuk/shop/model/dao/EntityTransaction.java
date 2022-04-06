package by.matsuk.shop.model.dao;

import by.matsuk.shop.model.connection.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @project Postcard shop "Card4You"
 * @author Ira
 * The type Entity transaction.
 */
public class EntityTransaction {
    static final Logger logger = LogManager.getLogger();
    private Connection connection;

    /**
     * Init transaction.
     *
     * @param daos the daos
     */
    public void initTransaction(AbstractDao...daos){
        if(connection == null){
            connection = ConnectionPool.getInstance().getConnection();
        }
        try{
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        for(AbstractDao daoElement: daos){
            daoElement.setConnection(connection);
        }
    }

    /**
     * End transaction.
     */
    public void endTransaction(){
        if(connection != null){
            try{
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                logger.error(e.getMessage());
            }
            ConnectionPool.getInstance().releaseConnection(connection);
            connection = null;
        }
    }

    /**
     * Commit.
     */
    public void commit(){
        try{
            connection.commit();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * Rollback.
     */
    public void rollback(){
        try{
            connection.rollback();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * Init.
     *
     * @param dao the dao
     */
    public void init(AbstractDao dao){
        if(connection == null){
            connection = ConnectionPool.getInstance().getConnection();
        }
        dao.setConnection(connection);
    }

    /**
     * End.
     */
    public void end(){
        if(connection != null){
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        connection = null;
    }
}
