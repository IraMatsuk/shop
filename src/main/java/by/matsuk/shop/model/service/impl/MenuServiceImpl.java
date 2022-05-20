package by.matsuk.shop.model.service.impl;

import by.matsuk.shop.entity.Postcard;
import by.matsuk.shop.exception.DaoException;
import by.matsuk.shop.exception.ServiceException;
import by.matsuk.shop.model.dao.AbstractDao;
import by.matsuk.shop.model.dao.EntityTransaction;
import by.matsuk.shop.model.dao.impl.MenuDaoImpl;
import by.matsuk.shop.model.service.MenuService;
import by.matsuk.shop.validator.Validator;
import by.matsuk.shop.validator.impl.ValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.matsuk.shop.controller.Parameter.*;

public class MenuServiceImpl implements MenuService {
    private static final Logger logger = LogManager.getLogger();
    private static final MenuServiceImpl instance = new MenuServiceImpl();
    private final Validator validator = ValidatorImpl.getInstance();

    private MenuServiceImpl() {
    }

    /**
     * Get instance menu service.
     *
     * @return the menu service
     */
    public static MenuServiceImpl getInstance() {
        return instance;
    }

    @Override
    public List<Postcard> findPostcardsSublist(int pageSize, int offset) throws ServiceException {
        MenuDaoImpl menuDao = new MenuDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(menuDao);
        logger.info("Parameter: " + pageSize + " " + offset);
        try {
            return menuDao.findPostcardSublist(pageSize, offset);
        } catch (DaoException e) {
            throw new ServiceException("Exception in a findPostcardSublist method. ", e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public List<Postcard> findPostcardsSublistBySectionId(int pageSize, int offset, long sectionId) throws ServiceException {
        MenuDaoImpl menuDao = new MenuDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(menuDao);
        logger.info("Parameter: " + pageSize + " " + offset);
        try {
            return menuDao.findPostcardSublistBySectionId(pageSize, offset, sectionId);
        } catch (DaoException e) {
            throw new ServiceException("Exception in a findMenuSublist service method. ", e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public boolean addNewProduct(Map<String, String> map, String defaultImage) throws ServiceException {
        if (!validator.checkProductData(map)) {
            return false;
        }
        MenuDaoImpl menuDao = new MenuDaoImpl();
        EntityTransaction entityTransaction = new EntityTransaction();
        entityTransaction.init(menuDao);
        try {
            String name = map.get(PRODUCT_NAME);
            if (menuDao.findPostcardByName(name).isPresent()) {
                map.put(PRODUCT_NAME, NOT_UNIQ_PRODUCT_NAME);
                return false;
            }
            String author = map.get(PRODUCT_AUTHOR);
            String description = map.get(PRODUCT_DESCRIPTION);
            BigDecimal discount = BigDecimal.valueOf(Double.parseDouble(map.get(PRODUCT_DISCOUNT)));
            BigDecimal price = BigDecimal.valueOf(Double.parseDouble(map.get(PRODUCT_PRICE)));
            long sectionId = Long.parseLong(map.get(PRODUCT_SECTION));
            Postcard postcard = new Postcard(name, author, defaultImage, description, discount, price, sectionId, true);
            return menuDao.create(postcard);
        } catch (DaoException e) {
            throw new ServiceException("Exception in a addNewProduct service method ", e);
        } finally {
            entityTransaction.end();
        }
    }

    @Override
    public boolean updateProductPhoto(String image, String name) throws ServiceException {
        MenuDaoImpl menuDao = new MenuDaoImpl();
        EntityTransaction entityTransaction = new EntityTransaction();
        entityTransaction.init(menuDao);
        try {
            return menuDao.updateImagePathByName(name, image);
        } catch (DaoException e) {
            throw new ServiceException("Exception in a updateProductPhoto service method ", e);
        } finally {
            entityTransaction.end();
        }
    }

    @Override
    public Optional<Postcard> findProductById(long id) throws ServiceException {
        AbstractDao<Postcard> abstractDao = new MenuDaoImpl();
        EntityTransaction entityTransaction = new EntityTransaction();
        entityTransaction.init(abstractDao);
        try {
            return abstractDao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException("Exception in a findProductById service method", e);
        } finally {
            entityTransaction.end();
        }
    }

    @Override
    public boolean deleteProductById(long id) throws ServiceException {
        AbstractDao<Postcard> abstractDao = new MenuDaoImpl();
        EntityTransaction entityTransaction = new EntityTransaction();
        entityTransaction.init(abstractDao);
        try {
            return abstractDao.delete(id);
        } catch (DaoException e) {
            throw new ServiceException("Exception in a deleteProductById service method ", e);
        } finally {
            entityTransaction.end();
        }
    }

    @Override
    public Optional<Postcard> updateProduct(long id, Map<String, String> updateData) throws ServiceException {
        if (!validator.checkProductData(updateData)) {
            return Optional.empty();
        }
        MenuDaoImpl menuDao = new MenuDaoImpl();
        EntityTransaction entityTransaction = new EntityTransaction();
        entityTransaction.init(menuDao);
        try {
            String name = updateData.get(PRODUCT_NAME);
            logger.info("name = " + name);
            if (menuDao.findPostcardByName(name).isPresent() && menuDao.findById(id).isPresent()) {
                Postcard findMenu = menuDao.findPostcardByName(name).get();
                if (!findMenu.getPostcardName().equals(menuDao.findById(id).get().getPostcardName())) {
                    updateData.put(PRODUCT_NAME, NOT_UNIQ_PRODUCT_NAME);
                    return Optional.empty();
                }
            }
            String author = updateData.get(PRODUCT_AUTHOR);
            String description = updateData.get(PRODUCT_DESCRIPTION);
            BigDecimal discount = BigDecimal.valueOf(Double.parseDouble(updateData.get(PRODUCT_DISCOUNT)));
            BigDecimal price = BigDecimal.valueOf(Double.parseDouble(updateData.get(PRODUCT_PRICE)));
            long sectionId = Long.parseLong(updateData.get(PRODUCT_SECTION));
            Postcard newMenu = new Postcard(id, name, author, description, discount, price, sectionId, true);
            return menuDao.update(newMenu);
        } catch (DaoException e) {
            throw new ServiceException("Exception in a updateProduct service method ", e);
        } finally {
            entityTransaction.end();
        }
    }

    @Override
    public boolean deleteProductFromBasket(Map<Postcard, Integer> map, long id) throws ServiceException {
        AbstractDao<Postcard> abstractDao = new MenuDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(abstractDao);
        try {
            Optional<Postcard> menu = abstractDao.findById(id);
            if (menu.isPresent()) {
                return map.remove(menu.get()) != null;
            }

        } catch (DaoException e) {
            throw new ServiceException("Exception in a deleteProductFromBasket service method ", e);
        } finally {
            transaction.end();
        }
        return false;
    }

    @Override
    public boolean addProductToBasket(Map<Postcard, Integer> map, long id, int numberProduct) throws ServiceException {
        AbstractDao<Postcard> abstractDao = new MenuDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(abstractDao);
        try {
            Optional<Postcard> menu = abstractDao.findById(id);
            if (menu.isPresent() && map.containsKey(menu.get())) {
                int value = map.get(menu.get()) + numberProduct;
                map.put(menu.get(), value);
                return true;
            }
            if (menu.isPresent()) {
                map.put(menu.get(), numberProduct);
                return true;
            }
        } catch (DaoException e) {
            throw new ServiceException("Exception in a addProductToBasket service method ", e);
        } finally {
            transaction.end();
        }
        return false;
    }

    @Override
    public int readRowCount() throws ServiceException {
        MenuDaoImpl menuDao = new MenuDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(menuDao);
        try {
            return menuDao.readRowCount();
        } catch (DaoException e) {
            throw new ServiceException("Exception in a readRowCount service method. ", e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public List<Postcard> sortAllPostcardsByPrice(int pageSize, int offset) throws ServiceException {
        MenuDaoImpl menuDao = new MenuDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(menuDao);
        try {
            logger.info("page size = " + pageSize + " offset = " + offset);
            return menuDao.findAllSortedPostcardsByPrice(pageSize, offset);
        } catch (DaoException e) {
            throw new ServiceException("Exception in a sortAllPostcardsByPrice service method. ", e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public List<Postcard> sortSectionPostcardsByPrice(int pageSize, int offset, long sectionId) throws ServiceException {
        MenuDaoImpl menuDao = new MenuDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(menuDao);
        try {
            return menuDao.findSortedSectionPostcardsByPrice(pageSize, offset, sectionId);
        } catch (DaoException e) {
            throw new ServiceException("Exception in a sortSectionPostcardsByPrice service method. ", e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public int readRowCountBySection(long sectionId) throws ServiceException {
        MenuDaoImpl menuDao = new MenuDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(menuDao);
        try {
            return menuDao.readRowCountBySection(sectionId);
        } catch (DaoException e) {
            throw new ServiceException("Exception in a readRowCountBySection service method. ", e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public List<Postcard> findAllRemovingPostcards() throws ServiceException {
        MenuDaoImpl menuDao = new MenuDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(menuDao);
        try {
            return menuDao.findAllRemovingPostcards();
        } catch (DaoException e) {
            throw new ServiceException("Exception in a findAllRemovingPostcards service method ", e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public boolean restorePostcardsProductById(long menuId) throws ServiceException {
        MenuDaoImpl menuDao = new MenuDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(menuDao);
        try {
            return menuDao.restorePostcardById(menuId);
        } catch (DaoException e) {
            throw new ServiceException("Exception in a restorePostcardsProductById method ", e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public List<Postcard> findSortedPostcardsSubListByPopularity(int pageSize, int offset) throws ServiceException {
        MenuDaoImpl menuDao = new MenuDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(menuDao);
        try {
            return menuDao.findAllSortedPostcardByPopularity(pageSize, offset);
        } catch (DaoException e) {
            throw new ServiceException("Exception in a findSortedPostcardsSubListByPopularity service method", e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public List<Postcard> findSortedPostcardsSectionSubListByPopularity(int pageSize, int offset, long sectionId) throws ServiceException {
        MenuDaoImpl menuDao = new MenuDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(menuDao);
        try {
            return menuDao.findAllSortedSectionPostcardByPopularity(pageSize, offset, sectionId);
        } catch (DaoException e) {
            throw new ServiceException("Exception in a findSortedPostcardsSectionSubListByPopularity service method", e);
        } finally {
            transaction.end();
        }
    }
}
