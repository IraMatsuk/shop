package by.matsuk.shop.model.dao;

import by.matsuk.shop.entity.Postcard;
import by.matsuk.shop.exception.DaoException;

import java.util.List;
import java.util.Optional;

/**
 * The interface Postcard dao.
 */
public interface PostcardDao {
    /**
     * Update image path by name boolean.
     *
     * @param name     the name
     * @param filePath the file path
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updateImagePathByName(String name, String filePath) throws DaoException;

    /**
     * Find food by name optional.
     *
     * @param name the name
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<Postcard> findPostcardByName(String name) throws DaoException;

    /**
     * Find postcard sublist by section id list.
     *
     * @param pageSize  the page size
     * @param offset    the offset
     * @param sectionId the section id
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Postcard> findPostcardSublistBySectionId(int pageSize, int offset, long sectionId) throws DaoException;

    /**
     * Find postcard sublist list.
     *
     * @param pageSize the page size
     * @param offset   the offset
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Postcard> findPostcardSublist(int pageSize, int offset) throws DaoException;

    /**
     * Read row count int.
     *
     * @return the int
     * @throws DaoException the dao exception
     */
    int readRowCount() throws DaoException;

    /**
     * Find all sorted postcards list by price.
     *
     * @param pageSize the page size
     * @param offset   the offset
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Postcard> findAllSortedPostcardsByPrice(int pageSize, int offset) throws DaoException;

    /**
     * Find sorted section postcards list.
     *
     * @param pageSize  the page size
     * @param offset    the offset
     * @param sectionId the section id
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Postcard> findSortedSectionPostcardsByPrice(int pageSize, int offset, long sectionId) throws DaoException;

    /**
     * Find all sorted postcard by popularity list.
     *
     * @param pageSize the page size
     * @param offset   the offset
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Postcard> findAllSortedPostcardByPopularity(int pageSize, int offset) throws DaoException;

    /**
     * Find all sorted section postcard by popularity list.
     *
     * @param pageSize  the page size
     * @param offset    the offset
     * @param sectionId the section id
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Postcard> findAllSortedSectionPostcardByPopularity(int pageSize, int offset, long sectionId) throws DaoException;
    /**
     * Read row count by section int.
     *
     * @param sectionId the section id
     * @return the int
     * @throws DaoException the dao exception
     */
    int readRowCountBySection(long sectionId) throws DaoException;

    /**
     * Delete postcard by section id boolean.
     *
     * @param sectionId the section id
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean deletePostcardBySectionId(long sectionId) throws DaoException;

    /**
     * Restore postcard by id boolean.
     *
     * @param postcardId the menu id
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean restorePostcardById(long postcardId) throws DaoException;

    /**
     * Find all removing postcard list.
     *
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Postcard> findAllRemovingPostcards() throws DaoException;

    /**
     * Restore all postcards by section id boolean.
     *
     * @param sectionId the section id
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean restoreAllPostcardsBySectionId(long sectionId) throws DaoException;
}

