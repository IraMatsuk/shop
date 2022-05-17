package by.matsuk.shop.model.service.impl;

import by.matsuk.shop.entity.*;
import by.matsuk.shop.exception.DaoException;
import by.matsuk.shop.exception.ServiceException;
import by.matsuk.shop.model.dao.EntityTransaction;
import by.matsuk.shop.model.dao.impl.OrderDaoImpl;
import by.matsuk.shop.model.dao.impl.UserDaoImpl;
import by.matsuk.shop.model.dao.impl.UserDiscountDaoImpl;
import by.matsuk.shop.model.service.OrderService;
import by.matsuk.shop.util.mail.Mail;
import by.matsuk.shop.validator.Validator;
import by.matsuk.shop.validator.impl.ValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.matsuk.shop.controller.Parameter.*;

public class OrderServiceImpl implements OrderService {
    private static final Logger logger = LogManager.getLogger();
    private static final String EMAIL_ORDER_MESSAGE = "The order has been successfully placed";
    private static final String EMAIL_SUBJECT = "Card4You order";
    private static final OrderServiceImpl instance = new OrderServiceImpl();
    private final Validator validator = ValidatorImpl.getInstance();

    private OrderServiceImpl() {
    }

    /**
     * Get instance order service.
     *
     * @return the order service
     */
    public static OrderServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean createOrder(Map<Postcard, Integer> productMap, Map<String, String> orderInfo, User user, BigDecimal totalPrice) throws ServiceException {
        if (!validator.checkOrderInfo(orderInfo)) {
            return false;
        }
        OrderDaoImpl orderDao = new OrderDaoImpl();
        UserDaoImpl userDao = new UserDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.initTransaction(orderDao, userDao);
        String address = orderInfo.get(ADDRESS);
        boolean isCreate = true;
        try {
            Order newOrder = new Order(LocalDateTime.now(), Order.OrderState.CREATED, address, totalPrice, user.getUserId());
            logger.info(newOrder);
            long generatedKey = orderDao.createOrder(newOrder);
            if (generatedKey == 0) {
                isCreate = false;
            }
            logger.info("generatedKey = " + generatedKey);
            orderDao.createOrderMenu(generatedKey, productMap);
            logger.info("createOrderMenu");
            userDao.updateUserState(user.getUserId(), User.UserState.ACTIVE.getStateId());
            logger.info("updateUserState");
            Mail.createMail(user.getEmail(), EMAIL_SUBJECT, EMAIL_ORDER_MESSAGE);
            transaction.commit();
        } catch (DaoException e) {
            transaction.rollback();
            throw new ServiceException("Exception in a createOrder method. ", e);
        } finally {
            transaction.endTransaction();
        }
        return isCreate;
    }

    @Override
    public int calculateOrdersNumberPerYear(long userId) throws ServiceException {
        OrderDaoImpl orderDao = new OrderDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(orderDao);
        try {
            return orderDao.findNumberYearOrdersByUserId(userId);
        } catch (DaoException e) {
            throw new ServiceException("Exception in a calculateProductsNumberPerYear method. ", e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public List<Order> findAllUserOrders(User user) throws ServiceException {
        OrderDaoImpl orderDao = new OrderDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(orderDao);
        try {
            return orderDao.findAllUserOrders(user);
        } catch (DaoException e) {
            throw new ServiceException("Exception in a findAllUserOrders method. ", e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public List<CompleteOrder> findAllOrders() throws ServiceException {
        OrderDaoImpl orderDao = new OrderDaoImpl();
        UserDaoImpl userDao = new UserDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.initTransaction(orderDao, userDao);
        List<CompleteOrder> completeOrders = new ArrayList<>();
        try {
            List<Order> orderList = orderDao.findAll();
            for (Order order : orderList) {
                Optional<User> optionalUser = userDao.findUserByOrder(order.getOrderId());
                if (optionalUser.isPresent()) {
                    User user = optionalUser.get();
                    List<ComponentOrder> menuList = orderDao.findAllMenuOrder(order.getOrderId());
                    logger.info("Menu list size " + menuList.size());
                    logger.info("Order date: " + order.getOrderDate());
                    completeOrders.add(new CompleteOrder(user, order, menuList));
                } else {
                    logger.info("The user doesn't exist. Order ID is " + order.getOrderId());
                }
            }
            transaction.commit();
        } catch (DaoException e) {
            transaction.rollback();
            throw new ServiceException("Exception in a findAllOrders method. ", e);
        } finally {
            transaction.endTransaction();
        }
        return completeOrders;
    }

    @Override
    public boolean changeOrderStateById(long orderId, Order.OrderState state) throws ServiceException {
        OrderDaoImpl orderDao = new OrderDaoImpl();
        UserDaoImpl userDao = new UserDaoImpl();
        UserDiscountDaoImpl userDiscountDao = new UserDiscountDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.initTransaction(orderDao, userDao, userDiscountDao);
        try {
            boolean result;
            switch (state) {
                case PROCESSING, COMPLETED, CANCELLED, SENT, PAYED, CREATED -> {
                    result = orderDao.updateOrderStateById(orderId, state);
                    Optional<User> optionalUser = userDao.findUserByOrder(orderId);
                    if (optionalUser.isPresent()) {
                        User user = optionalUser.get();
                        int numberOrders = orderDao.findNumberYearOrdersByUserId(user.getUserId());
                        logger.info("numberOrders = " + numberOrders);
                        long discount_id = userDiscountDao.findDiscountIdByNumberOrders(numberOrders);
                        logger.info("discount_id = " + discount_id);
                        if (discount_id > 0 && discount_id != user.getDiscountId()) {
                            userDao.updateUserDiscountIdByUserId(user.getUserId(), discount_id);
                            logger.info("updateUserDiscountIdByUserId was successful");
                        }
                    }
                }
                default -> result = false;
            }
            transaction.commit();
            return result;
        } catch (DaoException e) {
            transaction.rollback();
            throw new ServiceException("Exception in a changeOrderStateById method. ", e);
        } finally {
            transaction.endTransaction();
        }
    }

    @Override
    public boolean deleteOldOrders() throws ServiceException {
        OrderDaoImpl orderDao = new OrderDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(orderDao);
        try {
            return orderDao.deleteOrders();
        } catch (DaoException e) {
            throw new ServiceException("Exception in deleteOldOrders service method. ", e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public List<Order> findAllSortedOrdersByDate() throws ServiceException {
        OrderDaoImpl orderDao = new OrderDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(orderDao);
        try {
            return orderDao.findAllSortedOrdersByDate();
        } catch (DaoException e) {
            throw new ServiceException("Exception in a findAllSortedOrdersByDate service method", e);
        } finally {
            transaction.end();
        }
    }
}