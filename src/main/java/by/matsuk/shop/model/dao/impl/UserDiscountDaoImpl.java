package by.matsuk.shop.model.dao.impl;

import by.matsuk.shop.entity.UserDiscount;
import by.matsuk.shop.exception.DaoException;
import by.matsuk.shop.model.dao.AbstractDao;
import by.matsuk.shop.model.dao.UserDiscountDao;

import by.matsuk.shop.model.mapper.impl.UserDiscountMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The type UserDiscountDaoImpl class executes requests to the DB.
 */
public class UserDiscountDaoImpl extends AbstractDao<UserDiscount> implements UserDiscountDao {
    private static final Logger logger = LogManager.getLogger();
    private static final int ONE_UPDATE = 1;
    private static final String SQL_SELECT_ALL_DISCOUNTS =
            "SELECT discount_id, discount, year_orders FROM personal_discounts";
    private static final String SQL_SELECT_ENTITY_BY_ID =
            "SELECT discount_id, discount, year_orders FROM personal_discounts WHERE discount_id = ?";
    private static final String SQL_INSERT_NEW_DISCOUNT =
            "INSERT INTO personal_discounts(discount, year_orders) VALUES (?, ?)";
    private static final String SQL_UPDATE_ROW_DISCOUNT =
            "UPDATE personal_discounts SET discount = (?), year_orders = (?) WHERE discount_id = (?)";
    private static final String SQL_SELECT_USER_DISCOUNT_ID_BY_NUMBER_ORDERS = """
            SELECT discount_id FROM personal_discounts
            JOIN (SELECT IF(MAX(discount) IS NULL, (SELECT MIN(discount) FROM personal_discounts) , MAX(discount))
            AS max_discount FROM personal_discounts
            WHERE year_orders < (?)
            ORDER BY discount) AS max_personal_discount
            ON max_personal_discount.max_discount = personal_discounts.discount""";

    @Override
    public List<UserDiscount> findAll() throws DaoException {
        List<UserDiscount> userDiscountList = new ArrayList<>();
        try(PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_SELECT_ALL_DISCOUNTS)){
            try(ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Optional<UserDiscount> optionalUserDiscount = new UserDiscountMapper().mapRow(resultSet);
                    optionalUserDiscount.ifPresent(userDiscountList::add);
                }
            }
            logger.info("The list of discounts: " + userDiscountList);
        }catch (SQLException e){
            logger.error("Exception while find all discounts method ");
            throw new DaoException("Exception in a find all discounts method. ", e);
        }
        return userDiscountList;
    }

    @Override
    public Optional<UserDiscount> findById(long id) throws DaoException {
        try(PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_SELECT_ENTITY_BY_ID)){
            statement.setLong(1,id);
            try(ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new UserDiscountMapper().mapRow(resultSet);
                }
            }
        }catch (SQLException e) {
            logger.error("Exception while find discount method ");
            throw new DaoException("Exception in a find discount method. ", e);
        }
        return Optional.empty();
    }

    @Override
    public boolean delete(long id) throws DaoException {
        throw new UnsupportedOperationException("Can't delete discount!");
    }

    @Override
    public boolean create(UserDiscount entity) throws DaoException {
        try(PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_INSERT_NEW_DISCOUNT)){
            statement.setBigDecimal(1,entity.getDiscount());
            statement.setInt(2,entity.getYearOrders());
            int rowUpdate = statement.executeUpdate();
            logger.info("rowUpdate equals: " + rowUpdate);
            return rowUpdate == ONE_UPDATE;
        } catch (SQLException e) {
            logger.error("Exception while create user discount method ");
            throw new DaoException("Exception in a create user discount method. ", e);
        }
    }

    @Override
    public Optional<UserDiscount> update(UserDiscount entity) throws DaoException {
        try(PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_UPDATE_ROW_DISCOUNT)){
            Optional<UserDiscount> userDiscount = findById(entity.getDiscountId());
            statement.setBigDecimal(1,entity.getDiscount());
            statement.setInt(2,entity.getYearOrders());
            statement.setLong(3,entity.getDiscountId());
            logger.info("The new row: " + entity);
            return statement.executeUpdate() == ONE_UPDATE ? userDiscount  : Optional.empty();
        }catch (SQLException e) {
            logger.error("Exception while update user discount method ");
            throw new DaoException("Exception in a update user discount method. ", e);
        }
    }

    @Override
    public long findDiscountIdByNumberOrders(int numberOrders) throws DaoException {
        try(PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_SELECT_USER_DISCOUNT_ID_BY_NUMBER_ORDERS)){
            statement.setInt(1, numberOrders);
            try(ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() ? resultSet.getLong(1) : -1L;
            }
        } catch (SQLException e) {
            logger.error("Exception in a findDiscountIdByNumberOrders method ");
            throw new DaoException("Exception in a findDiscountIdByNumberOrders method. ", e);
        }
    }
}