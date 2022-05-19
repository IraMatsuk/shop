package by.matsuk.shop.model.mapper.impl;

import by.matsuk.shop.entity.User;
import by.matsuk.shop.model.mapper.CustomRowMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static by.matsuk.shop.model.mapper.impl.UserDiscountMapper.DISCOUNT_ID;

/**
 * @author Ira
 * @project Postcard shop
 * The type User mapper.
 */
public class UserMapper implements CustomRowMapper<User> {
    private static final Logger logger = LogManager.getLogger();

    /**
     * The constant USER_ID.
     */
    public static final String USER_ID = "user_id";
    /**
     * The constant FIRST_NAME.
     */
    public static final String FIRST_NAME = "first_name";
    /**
     * The constant LAST_NAME.
     */
    public static final String LAST_NAME = "last_name";
    /**
     * The constant LOGIN.
     */
    public static final String LOGIN = "login";
    /**
     * The constant PASSWORD.
     */
    public static final String PASSWORD = "password";
    /**
     * The constant EMAIL.
     */
    public static final String EMAIL = "email";
    /**
     * The constant PHONE_NUMBER.
     */
    public static final String PHONE_NUMBER = "phone";
    /**
     * The constant USER_ROLE.
     */
    public static final String USER_ROLE = "role_name";

    /**
     * The constant USER_STATE.
     */
    public static final String USER_STATE = "state_name";

    @Override
    public Optional<User> mapRow(ResultSet resultSet) {
        User user = new User();
        Optional<User> optionalUser;
        try {
            user.setUserId(resultSet.getLong(USER_ID));
            user.setFirstName(resultSet.getString(FIRST_NAME));
            user.setLastName(resultSet.getString(LAST_NAME));
            user.setLogin(resultSet.getString(LOGIN));
            user.setPassword(resultSet.getString(PASSWORD));
            user.setEmail(resultSet.getString(EMAIL));
            user.setPhoneNumber(resultSet.getInt(PHONE_NUMBER));
            user.setDiscountId(resultSet.getLong(DISCOUNT_ID));
            user.setRole(User.UserRole.valueOf(resultSet.getString(USER_ROLE)
                    .trim().toUpperCase()));
            user.setState(User.UserState.valueOf(resultSet.getString(USER_STATE)
                    .trim().toUpperCase()));
            optionalUser = Optional.of(user);
        } catch (SQLException e) {
            logger.info("User is empty: " + e);
            optionalUser = Optional.empty();
        }
        return optionalUser;
    }
}
