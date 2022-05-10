package by.matsuk.shop.model.service.impl;

import by.matsuk.shop.entity.UserDiscount;
import by.matsuk.shop.exception.DaoException;
import by.matsuk.shop.exception.ServiceException;
import by.matsuk.shop.model.dao.AbstractDao;
import by.matsuk.shop.model.dao.EntityTransaction;
import by.matsuk.shop.model.dao.impl.UserDiscountDaoImpl;
import by.matsuk.shop.model.service.UserDiscountService;

import java.util.Optional;


/**
 * The type UserDiscountService. This class contains business logic
 * for orders products users discounts.
 */
public class UserDiscountServiceImpl implements UserDiscountService {
    private static final UserDiscountServiceImpl instance = new UserDiscountServiceImpl();

    private UserDiscountServiceImpl(){}

    /**
     * Get instance user discount service.
     *
     * @return the user discount service
     */
    public static UserDiscountServiceImpl getInstance(){
        return instance;
    }
    @Override
    public Optional<UserDiscount> findDiscountById(long id) throws ServiceException {
        AbstractDao<UserDiscount> abstractDao = new UserDiscountDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(abstractDao);
        try {
            return abstractDao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException("Exception in a findDiscountById method", e);
        } finally {
            transaction.end();
        }
    }
}