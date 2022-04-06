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

import static by.matsuk.shop.controller.Parameter.*;
import static by.matsuk.shop.controller.PathPage.REGISTRATION_PAGE;
import static by.matsuk.shop.controller.PathPage.SIGN_PAGE;
import static by.matsuk.shop.controller.PropertiesKey.*;
import static by.matsuk.shop.controller.SessionAttribute.CURRENT_PAGE;
import static by.matsuk.shop.controller.SessionAttribute.USER;

public class RegistrationCommand implements Command {
    private final UserService service = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Map<String,String> mapData = new HashMap<>();
        mapData.put(USER_FIRST_NAME, request.getParameter(USER_FIRST_NAME));
        mapData.put(USER_LAST_NAME, request.getParameter(USER_LAST_NAME));
        mapData.put(LOGIN, request.getParameter(LOGIN));
        mapData.put(PASSWORD, request.getParameter(PASSWORD));
        mapData.put(USER_EMAIL, request.getParameter(USER_EMAIL));
        mapData.put(USER_PHONE_NUMBER, request.getParameter(USER_PHONE_NUMBER));
        mapData.put(REPEAT_PASSWORD, request.getParameter(REPEAT_PASSWORD));
        Router router = new Router();
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(USER);
            User.UserRole role = user != null && user.getRole() == User.UserRole.ADMIN ?
                    User.UserRole.ADMIN : User.UserRole.CLIENT;

            if (service.userRegistration(mapData, role)) {
                router.setRedirectType();
                if(role == User.UserRole.ADMIN){
                    String currentPage = (String) session.getAttribute(CURRENT_PAGE);
                    router.setCurrentPage(currentPage);
                }else {
                    router.setCurrentPage(SIGN_PAGE);
                }
            } else {
                for (String key : mapData.keySet()) {
                    String currentValue = mapData.get(key);
                    switch (currentValue) {
                        case INVALID_FIRST_NAME -> request.setAttribute(INVALID_FIRST_NAME, INVALID_FIRST_MESSAGE);
                        case INVALID_EMAIL -> request.setAttribute(INVALID_EMAIL, INVALID_EMAIL_MESSAGE);
                        case INVALID_LAST_NAME -> request.setAttribute(INVALID_LAST_NAME, INVALID_LAST_MESSAGE);
                        case INVALID_LOGIN -> request.setAttribute(INVALID_LOGIN, INVALID_LOGIN_MESSAGE);
                        case INVALID_PASSWORD -> request.setAttribute(INVALID_PASSWORD, INVALID_PASSWORD_MESSAGE);
                        case INVALID_PHONE_NUMBER -> request.setAttribute(INVALID_PHONE_NUMBER, INVALID_PHONE_NUMBER_MESSAGE);
                        case NOT_UNIQ_EMAIL -> request.setAttribute(INVALID_EMAIL, NOT_UNIQ_EMAIL_MESSAGE);
                        case NOT_UNIQ_LOGIN -> request.setAttribute(INVALID_LOGIN, NOT_UNIQ_LOGIN_MESSAGE);
                        case NOT_UNIQ_PHONE -> request.setAttribute(INVALID_PHONE_NUMBER, NOT_UNIQ_PHONE_MESSAGE);
                        case INVALID_REPEAT_PASSWORD -> request.setAttribute(INVALID_REPEAT_PASSWORD, INVALID_REPEAT_PASSWORD_MESSAGE);
                    }
                }
                router.setCurrentPage(REGISTRATION_PAGE);
            }
        } catch (ServiceException e) {
            throw new CommandException("Error while registration command", e);
        }
        return router;
    }
}
