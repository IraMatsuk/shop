package by.matsuk.shop.controller.command.impl.common;

import by.matsuk.shop.controller.Router;
import by.matsuk.shop.controller.command.Command;
import by.matsuk.shop.entity.Postcard;
import by.matsuk.shop.entity.Section;
import by.matsuk.shop.entity.User;
import by.matsuk.shop.exception.CommandException;
import by.matsuk.shop.exception.ServiceException;
import by.matsuk.shop.model.service.SectionService;
import by.matsuk.shop.model.service.UserService;
import by.matsuk.shop.model.service.impl.SectionServiceImpl;
import by.matsuk.shop.model.service.impl.UserServiceImpl;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static by.matsuk.shop.controller.Parameter.*;
import static by.matsuk.shop.controller.PathPage.HOME_PAGE;
import static by.matsuk.shop.controller.PathPage.SIGN_PAGE;
import static by.matsuk.shop.controller.PropertiesKey.ERROR_INCORRECT_LOGIN_OR_PASSWORD_MESSAGE;
import static by.matsuk.shop.controller.PropertiesKey.USER_BLOCKED_MESSAGE;
import static by.matsuk.shop.controller.SessionAttribute.*;

/**
 * The type Sign in command.
 */
public class SignInCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private final UserService userService = UserServiceImpl.getInstance();
    private final SectionService sectionService = SectionServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        ServletContext context = request.getServletContext();
        Router router = new Router();
        String login = request.getParameter(LOGIN);
        String pass = request.getParameter(PASSWORD);
        logger.info("login and pass " + login + " " + pass);
        try {
            Optional<User> optionalUser = userService.signIn(login, pass);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                logger.info("Sign in " + user.getRole());
                switch (user.getRole()){
                    case ADMIN -> {
                        session.setAttribute(USER, user);
                        router.setCurrentPage(HOME_PAGE);
                        List<Section> sectionList = sectionService.findAllSections();
                        context.setAttribute(SECTION_LIST, sectionList);
                    }
                    case CLIENT -> {
                        if(user.getState() == User.UserState.BLOCKED){
                            request.setAttribute(USER_STATUS_BLOCKED, USER_BLOCKED_MESSAGE);
                            router.setCurrentPage(SIGN_PAGE);
                        }else {
                            logger.info("Client page");
                            session.setAttribute(USER, user);
                            session.setAttribute(CART, new HashMap<Postcard, Integer>());
                            List<Section> sectionList = sectionService.findAllSections();
                            context.setAttribute(SECTION_LIST, sectionList);
                            router.setCurrentPage(HOME_PAGE);
                        }
                    }
                }
            } else {
                logger.debug("SignInCommand");
                request.setAttribute(ERROR_LOG_OR_PASS, ERROR_INCORRECT_LOGIN_OR_PASSWORD_MESSAGE);
                router.setCurrentPage(SIGN_PAGE);
            }
        } catch (ServiceException e) {
            throw new CommandException("Error during sign in ", e);
        }
        logger.info("SignInCommand");
        return router;
    }
}
