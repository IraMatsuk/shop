package by.matsuk.shop.model.mapper.impl;

import by.matsuk.shop.entity.Postcard;
import by.matsuk.shop.exception.DaoException;
import by.matsuk.shop.model.mapper.CustomRowMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static by.matsuk.shop.model.mapper.impl.SectionMapper.SECTION;

/**
 * The type MenuMapper. Extract menu rows form ResultSet.
 */
public class MenuMapper implements CustomRowMapper<Postcard> {
    private static final Logger logger = LogManager.getLogger();
    /**
     * The constant POSTCARD_ID.
     */
    public static final String POSTCARD_ID = "postcard_id";
    /**
     * The constant POSTCARD_NAME.
     */
    public static final String POSTCARD_NAME = "postcard_name";
    /**
     * The constant POSTCARD_AUTHOR.
     */
    public static final String POSTCARD_AUTHOR = "postcard_author";
    /**
     * The constant PICTURE_PATH.
     */
    public static final String PICTURE_PATH = "picture_path";
    /**
     * The constant DESCRIPTION.
     */
    public static final String DESCRIPTION = "description";
    /**
     * The constant DISCOUNT.
     */
    public static final String DISCOUNT = "discount";
    /**
     * The constant PRICE.
     */
    public static final String PRICE = "price";
    /**
     * The constant IS_ACCESSIBLE_MENU_PRODUCT.
     */
    public static final String IS_ACCESSIBLE_MENU_PRODUCT = "is_accessible";

    @Override
    public Optional<Postcard> mapRow(ResultSet resultSet) throws DaoException {
        Postcard postcard = new Postcard();
        Optional<Postcard> optionalMenu;
        try {
            postcard.setPostcardId(resultSet.getLong(POSTCARD_ID));
            postcard.setPostcardName(resultSet.getString(POSTCARD_NAME));
            postcard.setPostcardAuthor(resultSet.getString(POSTCARD_AUTHOR));
            postcard.setPicturePath(resultSet.getString(PICTURE_PATH));
            postcard.setDescription(resultSet.getString(DESCRIPTION));
            postcard.setDiscount(resultSet.getBigDecimal(DISCOUNT));
            postcard.setPrice(resultSet.getBigDecimal(PRICE));
            postcard.setSectionId(resultSet.getLong(SECTION));
            logger.info("Accessible - " + resultSet.getBoolean(IS_ACCESSIBLE_MENU_PRODUCT));
            postcard.setAccessible(resultSet.getBoolean(IS_ACCESSIBLE_MENU_PRODUCT));
            logger.info("Accessible - " + postcard.isAccessible());
            optionalMenu = Optional.of(postcard);
        } catch (SQLException e) {
            logger.warn("Not found menu item! ");
            optionalMenu = Optional.empty();
        }
        return optionalMenu;
    }
}
