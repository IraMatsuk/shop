package by.matsuk.shop.model.dao.impl;

import by.matsuk.shop.entity.Section;
import by.matsuk.shop.exception.DaoException;
import by.matsuk.shop.model.dao.AbstractDao;
import by.matsuk.shop.model.dao.SectionDao;
import by.matsuk.shop.model.mapper.impl.SectionMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The type Section dao.
 */
public class SectionDaoImpl extends AbstractDao<Section> implements SectionDao {
    private static final Logger logger = LogManager.getLogger();
    private static final int ONE_UPDATE = 1;
    private static final String SQL_SELECT_ALL_SECTIONS = """
            SELECT section_id, section_name, is_accessible FROM sections
            WHERE is_accessible = true""";
    private static final String SQL_INSERT_NEW_SECTION = """
            INSERT INTO sections(section_name, is_accessible) VALUES (?, ?)""";
    private static final String SQL_SELECT_SECTION_BY_NAME = """
            SELECT section_id, section_name, is_accessible FROM sections
            WHERE section_name = (?)""";
    private static final String SQL_SELECT_SECTION_BY_ID = """
            SELECT section_id, section_name, is_accessible FROM sections
            WHERE section_id = (?)""";
    private static final String SQL_UPDATE_SECTION_NAME = """
            UPDATE sections
            SET section_name = (?)
            WHERE section_id = (?)""";
    private static final String SQL_DELETE_SECTION_BY_ID = """
            UPDATE sections
            SET is_accessible = false
            WHERE section_id = (?)""";
    private static final String SQL_SELECT_ALL_REMOVING_SECTIONS = """
            SELECT section_id, section_name, is_accessible FROM sections
            WHERE is_accessible = false""";
    private static final String SQL_RESTORE_SECTION_BY_ID = """
            UPDATE sections
            SET is_accessible = true
            WHERE section_id = (?)""";

    @Override
    public List<Section> findAll() throws DaoException {
        List<Section> sectionList = new ArrayList<>();
        try(PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_SELECT_ALL_SECTIONS)){
            try(ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Optional<Section> optionalSection = new SectionMapper().mapRow(resultSet);
                    optionalSection.ifPresent(sectionList::add);
                }
            }
        } catch (SQLException e) {
            logger.error("Exception while find all sections ");
            throw new DaoException("Exception in a findAll method. ", e);
        }
        return sectionList;
    }

    @Override
    public Optional<Section> findById(long id) throws DaoException {
        try(PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_SELECT_SECTION_BY_ID)){
            statement.setLong(1, id);
            try(ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new SectionMapper().mapRow(resultSet);
                }
            }
        } catch (SQLException e) {
            logger.error("Exception while find section by id method ");
            throw new DaoException("Exception in a findEntityById section method. ", e);
        }
        return Optional.empty();
    }

    @Override
    public boolean delete(long id) throws DaoException {
        try(PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_DELETE_SECTION_BY_ID)){
            statement.setLong(1, id);
            return statement.executeUpdate() == ONE_UPDATE;
        } catch (SQLException e) {
            logger.error("Exception while delete section method ");
            throw new DaoException("Exception in a delete section method. ", e);
        }
    }


    @Override
    public boolean create(Section entity) throws DaoException {
        try(PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_INSERT_NEW_SECTION)){
            statement.setString(1, entity.getSectionName());
            statement.setBoolean(2, entity.isAccessible());
            return statement.executeUpdate() == ONE_UPDATE;
        } catch (SQLException e) {
            logger.error("Exception while create new section  ");
            throw new DaoException("Exception in a create section method. ", e);
        }
    }

    @Override
    public Optional<Section> update(Section entity) throws DaoException {
        try(PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_UPDATE_SECTION_NAME)){
            Optional<Section> section = findById(entity.getSectionId());
            statement.setString(1, entity.getSectionName());
            statement.setLong(2, entity.getSectionId());
            return statement.executeUpdate() == ONE_UPDATE ? section : Optional.empty();
        } catch (SQLException e) {
            logger.error("Exception while update section ");
            throw new DaoException("Exception in a update section method. ", e);
        }
    }

    @Override
    public Optional<Section> findSectionByName(String sectionName) throws DaoException {
        try(PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_SELECT_SECTION_BY_NAME)){
            statement.setString(1, sectionName);
            try(ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new SectionMapper().mapRow(resultSet);
                }
            }
        } catch (SQLException e) {
            logger.error("Exception while find section by name ");
            throw new DaoException("Exception in a findSectionByName method. ", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Section> findAllRemovingSections() throws DaoException {
        List<Section> sectionList = new ArrayList<>();
        try(PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_SELECT_ALL_REMOVING_SECTIONS);
            ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()){
                Optional<Section> optionalSection = new SectionMapper().mapRow(resultSet);
                optionalSection.ifPresent(sectionList::add);
            }
        } catch (SQLException e) {
            logger.error("Exception while reading removing sections ");
            throw new DaoException("Exception while reading removing sections ", e);
        }
        return sectionList;
    }

    @Override
    public boolean restoreSectionById(long sectionId) throws DaoException {
        try (PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_RESTORE_SECTION_BY_ID)){
            statement.setLong(1, sectionId);
            return statement.executeUpdate() == ONE_UPDATE;
        } catch (SQLException e) {
            logger.error("Exception while restoring section ");
            throw new DaoException("Exception while restoring section ", e);
        }
    }
}
