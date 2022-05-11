package by.matsuk.shop.model.service.impl;

import by.matsuk.shop.entity.Section;
import by.matsuk.shop.exception.DaoException;
import by.matsuk.shop.exception.ServiceException;
import by.matsuk.shop.model.dao.AbstractDao;
import by.matsuk.shop.model.dao.EntityTransaction;
import by.matsuk.shop.model.dao.impl.MenuDaoImpl;
import by.matsuk.shop.model.dao.impl.SectionDaoImpl;
import by.matsuk.shop.model.service.SectionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

/**
 * The type Section service.
 */
public class SectionServiceImpl implements SectionService {
    private static final Logger logger = LogManager.getLogger();
    private static final SectionServiceImpl instance = new SectionServiceImpl();

    private SectionServiceImpl() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static SectionServiceImpl getInstance() {
        return instance;
    }

    @Override
    public List<Section> findAllSections() throws ServiceException {
        AbstractDao<Section> abstractDao = new SectionDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(abstractDao);
        try {
            return abstractDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("Exception in a findAllSections method. ", e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public boolean addNewSection(String sectionName) throws ServiceException {
        AbstractDao<Section> abstractDao = new SectionDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(abstractDao);
        try {
            return abstractDao.create(new Section(sectionName, true));
        } catch (DaoException e) {
            throw new ServiceException("Exception in a addNewSection method. ", e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public Optional<Section> findSectionByName(String sectionName) throws ServiceException {
        SectionDaoImpl sectionDao = new SectionDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(sectionDao);
        try {
            return sectionDao.findSectionByName(sectionName);
        } catch (DaoException e) {
            throw new ServiceException("Exception in a findSectionByName method. ", e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public Optional<Section> updateSectionName(Section newSection) throws ServiceException {
        AbstractDao<Section> abstractDao = new SectionDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(abstractDao);
        try {
            return abstractDao.update(newSection);
        } catch (DaoException e) {
            throw new ServiceException("Exception in a updateSectionName method. ", e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public boolean deleteSectionById(long sectionId) throws ServiceException {
        AbstractDao<Section> abstractDao = new SectionDaoImpl();
        MenuDaoImpl menuDao = new MenuDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.initTransaction(abstractDao, menuDao);
        boolean isDelete;
        try {
            isDelete = abstractDao.delete(sectionId);
            menuDao.deletePostcardBySectionId(sectionId);
            transaction.commit();
        } catch (DaoException e) {
            transaction.rollback();
            throw new ServiceException("Exception in a deleteSectionById method. ", e);
        } finally {
            transaction.endTransaction();
        }
        return isDelete;
    }

    @Override
    public List<Section> findAllRemovingSections() throws ServiceException {
        SectionDaoImpl sectionDao = new SectionDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(sectionDao);
        try {
            return sectionDao.findAllRemovingSections();
        } catch (DaoException e) {
            throw new ServiceException("Exception in a findAllRemovingSections method", e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public boolean restoreSectionById(long sectionId) throws ServiceException {
        SectionDaoImpl sectionDao = new SectionDaoImpl();
        MenuDaoImpl menuDao = new MenuDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.initTransaction(sectionDao, menuDao);
        boolean isRestore;
        try {
            isRestore = sectionDao.restoreSectionById(sectionId);
            logger.info("isRestore = " + isRestore);
            menuDao.restoreAllPostcardsBySectionId(sectionId);
        } catch (DaoException e) {
            transaction.rollback();
            throw new ServiceException("Exception in a restoreSectionById service method ", e);
        } finally {
            transaction.endTransaction();
        }
        return isRestore;
    }
}
