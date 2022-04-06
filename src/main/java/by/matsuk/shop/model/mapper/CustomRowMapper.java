package by.matsuk.shop.model.mapper;

import by.matsuk.shop.entity.AbstractEntity;
import by.matsuk.shop.exception.DaoException;

import java.sql.ResultSet;
import java.util.Optional;

/**
 * @author Ira
 * @project Postcard shop
 * The interface Custom row mapper.
 *
 * @param <T> the type parameter
 */
@FunctionalInterface
public interface CustomRowMapper<T extends AbstractEntity> {
    /**
     * Map row optional.
     *
     * @param resultSet the result set
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<T> mapRow(ResultSet resultSet) throws DaoException;
}
