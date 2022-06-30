package by.matsuk.shop.model.service;

import by.matsuk.shop.entity.Postcard;
import by.matsuk.shop.exception.ServiceException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The interface Catalog service.
 */
public interface CatalogService {
    /**
     * Find postcards sublist list.
     *
     * @param pageSize the page size
     * @param offset   the offset
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Postcard> findPostcardsSublist(int pageSize, int offset) throws ServiceException;

    /**
     * Find postcards sublist by section id list.
     *
     * @param pageSize  the page size
     * @param offset    the offset
     * @param sectionId the section id
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Postcard> findPostcardsSublistBySectionId(int pageSize, int offset, long sectionId) throws ServiceException;

    /**
     * Add new product boolean.
     *
     * @param map          the map
     * @param defaultImage the default image
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean addNewProduct(Map<String, String> map, String defaultImage) throws ServiceException;

    /**
     * Update product photo boolean.
     *
     * @param image the image
     * @param name  the name
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean updateProductPhoto(String image, String name) throws ServiceException;

    /**
     * Find product by id optional.
     *
     * @param id the id
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<Postcard> findProductById(long id) throws ServiceException;

    /**
     * Delete product by id boolean.
     *
     * @param id the id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean deleteProductById(long id) throws ServiceException;

    /**
     * Update product optional.
     *
     * @param id         the id
     * @param updateData the update data
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<Postcard> updateProduct(long id, Map<String, String> updateData) throws ServiceException;

    /**
     * Delete product from basket boolean.
     *
     * @param map the map
     * @param id  the id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean deleteProductFromBasket(Map<Postcard, Integer> map, long id) throws ServiceException;

    /**
     * Add product to basket boolean.
     *
     * @param map           the map
     * @param id            the id
     * @param numberProduct the number product
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean addProductToBasket(Map<Postcard, Integer> map, long id, int numberProduct) throws ServiceException;

    /**
     * Read row count int.
     *
     * @return the int
     * @throws ServiceException the service exception
     */
    int readRowCount() throws ServiceException;

    /**
     * Sort all postcards by price list.
     *
     * @param pageSize the page size
     * @param offset   the offset
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Postcard> sortAllPostcardsByPrice(int pageSize, int offset) throws ServiceException;

    /**
     * Sort section postcards by price list.
     *
     * @param pageSize  the page size
     * @param offset    the offset
     * @param sectionId the section id
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Postcard> sortSectionPostcardsByPrice(int pageSize, int offset, long sectionId) throws ServiceException;

    /**
     * Read row count by section int.
     *
     * @param sectionId the section id
     * @return the int
     * @throws ServiceException the service exception
     */
    int readRowCountBySection(long sectionId) throws ServiceException;

    /**
     * Find all removing postcards list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Postcard> findAllRemovingPostcards() throws ServiceException;

    /**
     * Restore postcards product by id boolean.
     *
     * @param postcardId the postcard id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean restorePostcardsProductById(long postcardId) throws ServiceException;

    /**
     * Find sorted postcards sub list by popularity list.
     *
     * @param pageSize the page size
     * @param offset   the offset
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Postcard> findSortedPostcardsSubListByPopularity(int pageSize, int offset) throws ServiceException;

    /**
     * Find sorted postcards section sub list by popularity list.
     *
     * @param pageSize  the page size
     * @param offset    the offset
     * @param sectionId the section id
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Postcard> findSortedPostcardsSectionSubListByPopularity(int pageSize, int offset, long sectionId) throws ServiceException;
}

