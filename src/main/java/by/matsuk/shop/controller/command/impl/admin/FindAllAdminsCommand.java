package by.matsuk.shop.controller.command.impl.admin;

import by.matsuk.shop.controller.Router;
import by.matsuk.shop.controller.command.Command;
import by.matsuk.shop.entity.User;
import by.matsuk.shop.exception.CommandException;
import by.matsuk.shop.exception.ServiceException;
import by.matsuk.shop.model.service.UserService;
import by.matsuk.shop.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import static by.matsuk.shop.controller.Parameter.LIST_USER;
import static by.matsuk.shop.controller.PathPage.USERS_PAGE;

/**
 * The type Find all admins command.
 */
public class FindAllAdminsCommand implements Command {
    private final UserService service = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        try {
            router.setCurrentPage(USERS_PAGE);
            List<User> listAdmin = service.findAllAdmins();
            request.setAttribute(LIST_USER, listAdmin);
        } catch (ServiceException e) {
            throw new CommandException("Exception in a FindAllAdminsCommand class. ", e);
        }
        return router;
    }
}