package by.matsuk.shop.model.dao;

import by.matsuk.shop.entity.Section;
import by.matsuk.shop.exception.DaoException;

import java.util.List;
import java.util.Optional;

/**
 * The interface Section dao.
 */
public interface SectionDao {
    /**
     * Find section by name optional.
     *
     * @param sectionName the section name
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<Section> findSectionByName(String sectionName) throws DaoException;

    /**
     * Find all removing sections list.
     *
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Section> findAllRemovingSections() throws DaoException;

    /**
     * Restore section by id boolean.
     *
     * @param sectionId the section id
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean restoreSectionById(long sectionId) throws DaoException;
}
