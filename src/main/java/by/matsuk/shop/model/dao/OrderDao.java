package by.matsuk.shop.model.dao;

import by.matsuk.shop.entity.ComponentOrder;
import by.matsuk.shop.entity.Order;
import by.matsuk.shop.entity.Postcard;
import by.matsuk.shop.entity.User;
import by.matsuk.shop.exception.DaoException;

import java.util.List;
import java.util.Map;

/**
 * The interface Order dao.
 */
public interface OrderDao {
    /**
     * Find all user orders list.
     *
     * @param user the user
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Order> findAllUserOrders(User user) throws DaoException;

    /**
     * Update order state by id boolean.
     *
     * @param orderId    the order id
     * @param orderState the order state
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updateOrderStateById(long orderId, Order.OrderState orderState) throws DaoException;

    /**
     * Create order menu boolean.
     *
     * @param orderId         the order id
     * @param mapOrderProduct the map order product
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean createOrderMenu(long orderId, Map<Postcard, Integer> mapOrderProduct) throws DaoException;

    /**
     * Create order long.
     *
     * @param order the order
     * @return the long
     * @throws DaoException the dao exception
     */
    long createOrder(Order order) throws DaoException;

    /**
     * Find number year orders by user id int.
     *
     * @param userId the user id
     * @return the int
     * @throws DaoException the dao exception
     */
    int findNumberYearOrdersByUserId(long userId) throws DaoException;

    /**
     * Find all menu order list.
     *
     * @param orderId the order id
     * @return the list
     * @throws DaoException the dao exception
     */
    List<ComponentOrder> findAllMenuOrder(long orderId) throws DaoException;

    /**
     * Find all sorted orders by date list.
     *
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Order> findAllSortedOrdersByDate() throws DaoException;
}