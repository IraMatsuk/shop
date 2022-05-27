package by.matsuk.shop.controller.command.impl.admin;

import by.matsuk.shop.controller.Router;
import by.matsuk.shop.controller.command.Command;
import by.matsuk.shop.entity.User;
import by.matsuk.shop.exception.CommandException;
import by.matsuk.shop.exception.ServiceException;
import by.matsuk.shop.model.service.UserService;
import by.matsuk.shop.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static by.matsuk.shop.controller.Parameter.LIST_USER;
import static by.matsuk.shop.controller.PathPage.USERS_PAGE;

public class FindAllUsersCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        try {
            List<User> listUsers = userService.findAllClients();
            request.setAttribute(LIST_USER, listUsers);
            router.setCurrentPage(USERS_PAGE);
            logger.info(USERS_PAGE);
        } catch (ServiceException e) {
            throw new CommandException("Exception in a FindAllUsersCommand class ", e);
        }
        return router;
    }
}
