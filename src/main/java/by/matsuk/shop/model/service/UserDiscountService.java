package by.matsuk.shop.model.service;
import by.matsuk.shop.entity.UserDiscount;
import by.matsuk.shop.exception.ServiceException;

import java.util.Optional;

/**
 * The interface User discount service.
 */
public interface UserDiscountService {
    /**
     * Find discount by id optional.
     *
     * @param id the id
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<UserDiscount> findDiscountById(long id) throws ServiceException;
}