package by.matsuk.shop.controller.command.impl.admin;

import by.matsuk.shop.controller.Router;
import by.matsuk.shop.controller.command.Command;
import by.matsuk.shop.entity.User;
import by.matsuk.shop.exception.CommandException;
import by.matsuk.shop.exception.ServiceException;
import by.matsuk.shop.model.service.UserService;
import by.matsuk.shop.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static by.matsuk.shop.controller.Parameter.USER_ID;
import static by.matsuk.shop.controller.SessionAttribute.CURRENT_PAGE;
import static by.matsuk.shop.controller.SessionAttribute.USER;

/**
 * The type Delete user command.
 */
public class DeleteAdminCommand implements Command {
    private final UserService service = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(USER);
            User.UserState userState = user.getState();
            Router router = new Router();

            if (userState != User.UserState.ACTIVE) {
                long userId = Long.parseLong(request.getParameter(USER_ID));
                service.deleteAdmin(userId);
                //HttpSession session = request.getSession();
                String page = (String) session.getAttribute(CURRENT_PAGE);
                router.setCurrentPage(page);
            } else {
                String page = (String) session.getAttribute(CURRENT_PAGE);
                router.setCurrentPage(page);
            }
            return router;
        } catch (ServiceException | NumberFormatException e) {
            throw new CommandException("Exception in a DeleteUserCommand class ", e);
        }
    }
}
