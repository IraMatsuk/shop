package by.matsuk.shop.controller.command.impl.common;

import by.matsuk.shop.controller.Router;
import by.matsuk.shop.controller.command.Command;
import by.matsuk.shop.entity.User;
import by.matsuk.shop.exception.CommandException;
import by.matsuk.shop.exception.ServiceException;
import by.matsuk.shop.model.service.UserService;
import by.matsuk.shop.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static by.matsuk.shop.controller.Parameter.*;
import static by.matsuk.shop.controller.PropertiesKey.*;
import static by.matsuk.shop.controller.SessionAttribute.CURRENT_PAGE;
import static by.matsuk.shop.controller.SessionAttribute.USER;

/**
 * The type Update user profile command.
 */
public class UpdateUserProfileCommand implements Command {
    private final UserService service = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Map<String, String> updateProfileData = new HashMap<>();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        updateProfileData.put(USER_FIRST_NAME, request.getParameter(USER_FIRST_NAME));
        updateProfileData.put(USER_LAST_NAME, request.getParameter(USER_LAST_NAME));
        updateProfileData.put(USER_EMAIL, request.getParameter(USER_EMAIL));
        updateProfileData.put(USER_PHONE_NUMBER, request.getParameter(USER_PHONE_NUMBER));
        Router router = new Router();
        try {
            Optional<User> optionalUser = service.updateUserProfile(user, updateProfileData);
            String currentPage = (String) session.getAttribute(CURRENT_PAGE);
            router.setCurrentPage(currentPage);
            if (optionalUser.isPresent()) {
                session.setAttribute(USER, optionalUser.get());
                router.setRedirectType();
            } else {
                for (String key : updateProfileData.keySet()) {
                    String currentValue = updateProfileData.get(key);
                    switch (currentValue) {
                        case INVALID_FIRST_NAME -> request.setAttribute(INVALID_FIRST_NAME, INVALID_FIRST_MESSAGE);
                        case INVALID_EMAIL -> request.setAttribute(INVALID_EMAIL, INVALID_EMAIL_MESSAGE);
                        case INVALID_LAST_NAME -> request.setAttribute(INVALID_LAST_NAME, INVALID_LAST_MESSAGE);
                        case INVALID_PHONE_NUMBER -> request.setAttribute(INVALID_PHONE_NUMBER, INVALID_PHONE_NUMBER_MESSAGE);
                        case NOT_UNIQ_EMAIL -> request.setAttribute(INVALID_EMAIL, NOT_UNIQ_EMAIL_MESSAGE);
                        case NOT_UNIQ_PHONE -> request.setAttribute(INVALID_PHONE_NUMBER, NOT_UNIQ_PHONE_MESSAGE);
                    }
                }
            }
        } catch (ServiceException e) {
            throw new CommandException("Exception in a UpdateUserProfileCommand class", e);
        }
        return router;
    }
}
