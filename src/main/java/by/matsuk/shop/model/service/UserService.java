package by.matsuk.shop.model.service;

import by.matsuk.shop.entity.User;
import by.matsuk.shop.exception.ServiceException;

import java.util.Optional;

public interface UserService {
    Optional<User> findUser(String login, String password) throws ServiceException;
}
