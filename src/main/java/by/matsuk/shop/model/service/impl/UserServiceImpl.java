package by.matsuk.shop.model.service.impl;

import by.matsuk.shop.entity.User;
import by.matsuk.shop.exception.ServiceException;
import by.matsuk.shop.model.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String INCORRECT_VALUE_PARAMETER = "incorrect";
    private static final UserService instance = new UserServiceImpl();

    private UserServiceImpl() {
    }

    public static UserService getInstance() {
        return instance;
    }

    @Override
    public Optional<User> findUser(String login, String password) throws ServiceException {
        return Optional.empty(); //TODO
    }
}
