package by.matsuk.shop.model.dao;

import by.matsuk.shop.exception.DaoException;

/**
 * The interface User discount dao.
 */
public interface UserDiscountDao {
    /**
     * Find discount id by number orders long.
     *
     * @param numberOrders the number orders
     * @return the long
     * @throws DaoException the dao exception
     */
    long findDiscountIdByNumberOrders(int numberOrders) throws DaoException;
}