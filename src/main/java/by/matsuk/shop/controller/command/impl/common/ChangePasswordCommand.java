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
import static by.matsuk.shop.controller.PathPage.HOME_PAGE;
import static by.matsuk.shop.controller.PathPage.PASSWORD_PAGE;
import static by.matsuk.shop.controller.PropertiesKey.*;
import static by.matsuk.shop.controller.SessionAttribute.CURRENT_PAGE;
import static by.matsuk.shop.controller.SessionAttribute.USER;

/**
 * The type Change password command.
 */
public class ChangePasswordCommand implements Command {
    private final UserService service = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        String currentPage = (String) session.getAttribute(CURRENT_PAGE);
        router.setCurrentPage(currentPage);
        User user = (User) session.getAttribute(USER);
        Map<String, String> map = new HashMap<>();
        map.put(OLD_PASSWORD, request.getParameter(OLD_PASSWORD));
        map.put(NEW_PASSWORD, request.getParameter(NEW_PASSWORD));
        map.put(REPEAT_PASSWORD, request.getParameter(REPEAT_PASSWORD));
        try {
            boolean result = service.changePasswordByOldPassword(map, user);
            if(result){
               //router.setCurrentPage(PASSWORD_PAGE);
                router.setCurrentPage(request.getContextPath() + PASSWORD_PAGE);
                router.setRedirectType();

                session.setAttribute(USER, user);
            } else{
                for(String key: map.keySet()){
                    String value = map.get(key);
                    switch (value){
                        case INVALID_NEW_UNIQ_PASSWORD -> request.setAttribute(INVALID_NEW_UNIQ_PASSWORD, NOT_UNIQ_NEW_PASSWORD_MESSAGE);
                        case INVALID_NEW_PASSWORD -> request.setAttribute(INVALID_NEW_PASSWORD, INVALID_PASSWORD_MESSAGE);
                        case INVALID_OLD_PASSWORD -> request.setAttribute(INVALID_OLD_PASSWORD, INVALID_PASSWORD_MESSAGE);
                        case INVALID_REPEAT_PASSWORD -> request.setAttribute(INVALID_REPEAT_PASSWORD, INVALID_REPEAT_PASSWORD_MESSAGE);
                    }
                }
            }
        } catch (ServiceException e) {
            throw new CommandException("Exception in a ChangePasswordCommand class ", e);
        }
        return router;
    }
}
