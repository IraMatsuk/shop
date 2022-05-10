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

/**
 * The type Unblock user by id command.
 */
public class UnblockUserByIdCommand implements Command {
    private final UserService service = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        try {
            long id = Long.parseLong(request.getParameter(USER_ID));
            service.changeUserStateById(User.UserState.UNBLOCKED, id);
            router.setRedirectType();
            HttpSession session = request.getSession();
            String currentPage = (String) session.getAttribute(CURRENT_PAGE);
            router.setCurrentPage(currentPage);
        } catch (ServiceException | NumberFormatException e) {
            throw new CommandException("Exception in a BlockUserByIdCommand class ", e);
        }
        return router;
    }
}