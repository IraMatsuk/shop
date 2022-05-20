package by.matsuk.shop.model.dao.impl;

import by.matsuk.shop.entity.Postcard;
import by.matsuk.shop.exception.DaoException;
import by.matsuk.shop.model.dao.AbstractDao;
import by.matsuk.shop.model.dao.MenuDao;
import by.matsuk.shop.model.mapper.impl.MenuMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The type Menu dao.
 */
public class MenuDaoImpl extends AbstractDao<Postcard> implements MenuDao {
    private static final Logger logger = LogManager.getLogger();
    private static final int ONE_UPDATE = 1;
    private static final String SQL_SELECT_ALL_POSTCARDS =
            "SELECT postcard_id, postcard_name, postcard_author, picture_path, description, discount, price, section_id, is_accessible " +
            "FROM postcards WHERE is_accessible = true";
    private static final String SQL_SELECT_POSTCARD_BY_ID =
            "SELECT postcard_id, postcard_name, postcard_author, picture_path, description, discount, price, section_id, is_accessible " +
            "FROM postcards WHERE postcard_id = (?)";
    private static final String SQL_INSERT_NEW_POSTCARD_ITEM =
            "INSERT INTO postcards(postcard_name, postcard_author, picture_path, description, discount, price, section_id, is_accessible) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_DELETE_POSTCARD_ITEM =
            "UPDATE postcards SET is_accessible = false " +
            "WHERE postcard_id = (?)";
    private static final String SQL_DELETE_POSTCARD_BY_SECTION_ID =
            "UPDATE postcards SET is_accessible = false " +
            "WHERE section_id = (?)";
    private static final String SQL_UPDATE_POSTCARD =
            "UPDATE postcards " +
            "SET postcard_name = (?), postcard_author = (?), description = (?), discount = (?), price = (?), section_id = (?) " +
            "WHERE postcard_id = (?)";
    private static final String SQL_UPDATE_IMAGE_PATH_BY_NAME =
            "UPDATE postcards SET picture_path = (?) WHERE postcard_name = (?)";
    private static final String SQL_SELECT_POSTCARD_BY_NAME =
            "SELECT postcard_id, postcard_name, postcard_author, picture_path, description, discount, price, section_id, is_accessible " +
            "FROM postcards WHERE postcard_name = (?)";
    private static final String SQL_FIND_POSTCARD_SUBLIST_BY_SECTION_ID =
            "SELECT postcard_id, postcard_name, postcard_author, picture_path, description, discount, price, section_id, is_accessible " +
            "FROM postcards WHERE section_id = (?) AND is_accessible = true LIMIT ? OFFSET ?";
    private static final String SQL_SELECT_ALL_POSTCARDS_ROW_COUNT =
            "SELECT COUNT(*) FROM postcards WHERE is_accessible = true";
    private static final String SQL_SELECT_POSTCARD_SUBLIST =
            "SELECT postcard_id, postcard_name, postcard_author, picture_path, description, discount, price, section_id, is_accessible " +
            "FROM postcards WHERE is_accessible = true LIMIT ? OFFSET ?";
    private static final String SQL_SELECT_ALL_SORTED_POSTCARDS =
            "SELECT postcard_id, postcard_name, postcard_author, picture_path, description, discount, price, section_id, is_accessible " +
            "FROM postcards WHERE is_accessible = true " +
            "ORDER BY price - (price * discount / 100) LIMIT ? OFFSET ?";
    private static final String SQL_SELECT_ALL_SORTED_POSTCARDS_BY_POPULARITY = """
            SELECT postcards.postcard_id, postcard_name, postcard_author, picture_path, description,
            discount, price, section_id, is_accessible, all_postcards FROM postcards 
            LEFT JOIN (SELECT postcard_id, SUM(postcard_number) AS all_postcards FROM postcards_catalog 
            GROUP BY postcard_id) AS year_postcard ON year_postcard.postcard_id = postcards.postcard_id 
            WHERE is_accessible = true  
            ORDER BY all_postcards DESC  
            LIMIT ? OFFSET ?""";
    private static final String SQL_SELECT_SORTED_SECTION_POSTCARDS =
            "SELECT postcard_id, postcard_name, postcard_author, picture_path, description, " +
            "discount, price, section_id, is_accessible FROM postcards " +
            "WHERE section_id = ? AND is_accessible = true " +
            "ORDER BY price - (price * discount / 100) " +
            "LIMIT ? OFFSET ?";
    private static final String SQL_SELECT_ALL_SORTED_SECTION_POSTCARDS_BY_POPULARITY =
            "SELECT postcards.postcard_id, postcard_name, postcard_author, picture_path, description, " +
            "discount, price, section_id, is_accessible, all_postcards FROM postcards " +
            "LEFT JOIN (SELECT postcard_id, SUM(postcard_number) AS all_postcards FROM postcards_catalog " +
            "GROUP BY postcard_id) AS year_postcard ON year_postcard.postcard_id = postcards.postcard_id " +
            "WHERE is_accessible = true AND section_id = ? " +
            "ORDER BY all_postcards DESC " +
            "LIMIT ? OFFSET ?";
    private static final String SQL_SELECT_POSTCARDS_ROW_COUNT_BY_SECTION_ID =
            "SELECT COUNT(*) FROM postcards WHERE section_id = ? AND is_accessible = true";
    private static final String SQL_SELECT_ALL_REMOVING_POSTCARD_PRODUCTS =
            "SELECT postcard_id, postcard_name, postcard_author, picture_path, description, " +
            "discount, price, postcards.section_id, postcards.is_accessible FROM postcards " +
            "JOIN sections ON sections.section_id = postcards.section_id " +
            "WHERE postcards.is_accessible = false AND sections.is_accessible = true";
    private static final String SQL_RESTORE_POSTCARDS_BY_PRODUCT_ID =
            "UPDATE postcards " +
            "SET is_accessible = true " +
            "WHERE postcard_id = (?)";
    private static final String SQL_RESTORE_POSTCARDS_BY_SECTION_ID =
            "UPDATE postcards " +
            "SET is_accessible = true " +
            "WHERE section_id = (?)";

    @Override
    public List<Postcard> findAll() throws DaoException {
        List<Postcard> menuList = new ArrayList<>();
        try(PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_SELECT_ALL_POSTCARDS);
            ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()){
                Optional<Postcard> optionalMenu = new MenuMapper().mapRow(resultSet);
                if(optionalMenu.isPresent()) {
                    menuList.add(optionalMenu.get());
                    logger.info("Present");
                }
            }
        } catch (SQLException e) {
            logger.error("Exception while find all menu method ");
            throw new DaoException("Exception while find all menu method ", e);
        }
        return menuList;
    }

    @Override
    public Optional<Postcard> findById(long id) throws DaoException {
        try(PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_SELECT_POSTCARD_BY_ID)){
            statement.setLong(1,id);
            try(ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new MenuMapper().mapRow(resultSet);
                }
            }
        } catch (SQLException e) {
            logger.error("Exception while find menu by id method ");
            throw new DaoException("Exception while find menu by id method ", e);
        }
        logger.info("Menu item is empty ");
        return Optional.empty();
    }

    @Override
    public boolean delete(long id) throws DaoException {
        try(PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_DELETE_POSTCARD_ITEM)) {
            statement.setLong(1,id);
            return statement.executeUpdate() == ONE_UPDATE;
        } catch (SQLException e) {
            logger.error("Exception while delete menu by id method ");
            throw new DaoException("Exception while delete menu by id method ", e);
        }
    }

    @Override
    public boolean create(Postcard entity) throws DaoException {
        try(PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_INSERT_NEW_POSTCARD_ITEM)){
            statement.setString(1,entity.getPostcardName());
            statement.setString(2, entity.getPostcardAuthor());
            statement.setString(3,entity.getPicturePath());
            statement.setString(4, entity.getDescription());
            statement.setBigDecimal(5,entity.getDiscount());
            statement.setBigDecimal(6,entity.getPrice());
            statement.setLong(7,entity.getSectionId());
            statement.setBoolean(8, entity.isAccessible());
            return statement.executeUpdate() == ONE_UPDATE;
        } catch (SQLException e) {
            logger.error("Exception while create menu method ");
            throw new DaoException("Exception while create menu method ", e);
        }
    }
    @Override
    public Optional<Postcard> update(Postcard entity) throws DaoException {
        try (PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_UPDATE_POSTCARD)){
            Optional<Postcard> menu = findById(entity.getPostcardId());
            logger.info(entity.getPostcardName());
            statement.setString(1,entity.getPostcardName());
            logger.info(entity.getPostcardAuthor());
            statement.setString(2,entity.getPostcardAuthor());
            logger.info(entity.getDiscount());
            statement.setString(3, entity.getDescription());
            logger.info(entity.getDescription());
            statement.setBigDecimal(4,entity.getDiscount());
            logger.info(entity.getPrice());
            statement.setBigDecimal(5,entity.getPrice());
            logger.info(entity.getSectionId());
            statement.setLong(6, entity.getSectionId());
            logger.info(entity.getPostcardId());
            statement.setLong(7,entity.getPostcardId());
            return statement.executeUpdate() == ONE_UPDATE ? menu : Optional.empty();
        } catch (SQLException e) {
            logger.error("Exception while update menu method ");
            throw new DaoException("Exception while update menu method ", e);
        }
    }

    @Override
    public boolean updateImagePathByName(String name, String filePath) throws DaoException {
        try(PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_UPDATE_IMAGE_PATH_BY_NAME)){
            statement.setString(1,filePath);
            statement.setString(2,name);
            return statement.executeUpdate() == ONE_UPDATE;
        } catch (SQLException e) {
            logger.error("Exception while update image path by name menu method ");
            throw new DaoException("Exception while update image path by name menu method ", e);
        }
    }

    @Override
    public Optional<Postcard> findPostcardByName(String name) throws DaoException {
        Optional<Postcard> postcard = Optional.empty();
        try(PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_SELECT_POSTCARD_BY_NAME)){
            statement.setString(1,name);
            try(ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    postcard = new MenuMapper().mapRow(resultSet);
                }
            }
        } catch (SQLException e) {
            logger.error("Exception while find food by name method ");
            throw new DaoException("Exception while find food by name method ", e);
        }
        return postcard;
    }

    @Override
    public List<Postcard> findPostcardSublistBySectionId(int pageSize, int offset, long sectionId) throws DaoException {
        List<Postcard> postcardList = new ArrayList<>();
        try (PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_FIND_POSTCARD_SUBLIST_BY_SECTION_ID)){
            statement.setLong(1, sectionId);
            statement.setInt(2, pageSize);
            statement.setInt(3, offset);
            try(ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Optional<Postcard> optionalMenu = new MenuMapper().mapRow(resultSet);
                    optionalMenu.ifPresent(postcardList::add);
                }
            }
        } catch (SQLException e) {
            logger.error("Exception while find menu sublist by section id method ");
            throw new DaoException("Exception in a findMenuSublistBySection method. ", e);
        }
        return postcardList;
    }

    @Override
    public int readRowCount() throws DaoException {
        try (PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_SELECT_ALL_POSTCARDS_ROW_COUNT)){
            try(ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() ? resultSet.getInt(1) : 0;
            }
        } catch (SQLException e) {
            logger.error("Exception while read row count from menu table method ");
            throw new DaoException("Exception in a readRowCount method. ", e);
        }
    }

    @Override
    public List<Postcard> findPostcardSublist(int pageSize, int offset) throws DaoException {
        List<Postcard> postcardSublist = new ArrayList<>();
        try (PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_SELECT_POSTCARD_SUBLIST)){
            statement.setInt(1, pageSize);
            statement.setInt(2, offset);
            try(ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Optional<Postcard> optionalPostcard = new MenuMapper().mapRow(resultSet);
                    optionalPostcard.ifPresent(postcardSublist::add);
                }
            }
        } catch (SQLException e) {
            logger.error("Exception while find postcard sublist method ");
            throw new DaoException("Exception in a findMenuSublist method. ", e);
        }
        return postcardSublist;
    }

    @Override
    public List<Postcard> findAllSortedPostcardsByPrice(int pageSize, int offset) throws DaoException {
        List<Postcard> sortedList = new ArrayList<>();
        try(PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_SELECT_ALL_SORTED_POSTCARDS)){
            statement.setInt(1, pageSize);
            statement.setInt(2, offset);
            try(ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Optional<Postcard> optionalMenu = new MenuMapper().mapRow(resultSet);
                    optionalMenu.ifPresent(sortedList::add);
                }
            }
        } catch (SQLException e) {
            logger.error("Exception while find sorted postcard by price method ");
            throw new DaoException("Exception in a findAllSortedPostcards method. ", e);
        }
        return sortedList;
    }

    @Override
    public List<Postcard> findSortedSectionPostcardsByPrice(int pageSize, int offset, long sectionId) throws DaoException {
        List<Postcard> sortedList = new ArrayList<>();
        try(PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_SELECT_SORTED_SECTION_POSTCARDS)) {
            statement.setLong(1, sectionId);
            statement.setInt(2, pageSize);
            statement.setInt(3, offset);
            try(ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Optional<Postcard> optionalMenu = new MenuMapper().mapRow(resultSet);
                    optionalMenu.ifPresent(sortedList::add);
                }
            }
        } catch (SQLException e) {
            logger.error("Exception while find sorted postcard sublist by section id method ");
            throw new DaoException("Exception in a findSortedSectionPostcardByPrice method. ", e);
        }
        return sortedList;
    }
    @Override
    public List<Postcard> findAllSortedPostcardByPopularity(int pageSize, int offset) throws DaoException {
        List<Postcard> postcardList = new ArrayList<>();
        try(PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_SELECT_ALL_SORTED_POSTCARDS_BY_POPULARITY)){
            statement.setInt(1, pageSize);
            statement.setInt(2, offset);
            try(ResultSet resultSet = statement.executeQuery()){
                while (resultSet.next()){
                    Optional<Postcard> optionalMenu = new MenuMapper().mapRow(resultSet);
                    optionalMenu.ifPresent(postcardList::add);
                }
            }
        } catch (SQLException e) {
            logger.error("Exception while find all sorted postcard by popularity method ");
            throw new DaoException("Exception in a findAllSortedPostcardByPopularity method. ", e);
        }
        return postcardList;
    }

    @Override
    public List<Postcard> findAllSortedSectionPostcardByPopularity(int pageSize, int offset, long sectionId) throws DaoException {
        List<Postcard> postcardsList = new ArrayList<>();
        try(PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_SELECT_ALL_SORTED_SECTION_POSTCARDS_BY_POPULARITY)){
            statement.setLong(1, sectionId);
            statement.setInt(2, pageSize);
            statement.setInt(3, offset);
            try(ResultSet resultSet = statement.executeQuery()){
                while (resultSet.next()){
                    Optional<Postcard> optionalMenu = new MenuMapper().mapRow(resultSet);
                    optionalMenu.ifPresent(postcardsList::add);
                }
            }
        } catch (SQLException e) {
            logger.error("Exception while find all sorted postcard sublist by popularity method ");
            throw new DaoException("Exception in a findAllSortedSectionPostcardByPopularity method. ", e);
        }
        return postcardsList;
    }
    @Override
    public int readRowCountBySection(long sectionId) throws DaoException {
        try (PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_SELECT_POSTCARDS_ROW_COUNT_BY_SECTION_ID)){
            statement.setLong(1, sectionId);
            try(ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() ? resultSet.getInt(1) : 0;
            }
        } catch (SQLException e) {
            logger.error("Exception while read row count from menu by section table method ");
            throw new DaoException("Exception in a readRowCountBySection method. ", e);
        }
    }

    @Override
    public boolean deletePostcardBySectionId(long sectionId) throws DaoException {
        try(PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_DELETE_POSTCARD_BY_SECTION_ID)){
            statement.setLong(1, sectionId);
            return statement.executeUpdate() >= ONE_UPDATE;
        } catch (SQLException e) {
            logger.error("Exception while delete postcard by section id = " + sectionId);
            throw new DaoException("Exception while delete postcard by section id = " + sectionId, e);
        }
    }

    @Override
    public boolean restorePostcardById(long postcardId) throws DaoException {
        try(PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_RESTORE_POSTCARDS_BY_PRODUCT_ID)) {
            statement.setLong(1, postcardId);
            return statement.executeUpdate() == ONE_UPDATE;
        } catch (SQLException e) {
            logger.error("Exception while restoring postcard by postcard id ");
            throw new DaoException("Exception while restoring postcard by postcard id ", e);
        }
    }

    @Override
    public List<Postcard> findAllRemovingPostcards() throws DaoException {
        List<Postcard> postcardList = new ArrayList<>();
        try(PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_SELECT_ALL_REMOVING_POSTCARD_PRODUCTS);
            ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()){
                Optional<Postcard> optionalMenu = new MenuMapper().mapRow(resultSet);
                optionalMenu.ifPresent(postcardList::add);
            }
        } catch (SQLException e) {
            logger.error("Exception while reading removing postcards ");
            throw new DaoException("Exception while reading removing postcards ", e);
        }
        return postcardList;
    }

    @Override
    public boolean restoreAllPostcardsBySectionId(long sectionId) throws DaoException {
        try(PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_RESTORE_POSTCARDS_BY_SECTION_ID)) {
            statement.setLong(1, sectionId);
            return statement.executeUpdate() >= ONE_UPDATE;
        } catch (SQLException e) {
            logger.error("Exception while restoring postcards by section id ");
            throw new DaoException("Exception while restoring postcards by section id ", e);
        }
    }
}
