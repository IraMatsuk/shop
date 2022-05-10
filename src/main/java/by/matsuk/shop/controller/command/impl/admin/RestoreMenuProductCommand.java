package by.matsuk.shop.controller.command.impl.admin;

import by.matsuk.shop.controller.Router;
import by.matsuk.shop.controller.command.Command;
import by.matsuk.shop.exception.CommandException;
import by.matsuk.shop.exception.ServiceException;
import by.matsuk.shop.model.service.MenuService;
import by.matsuk.shop.model.service.impl.MenuServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static by.matsuk.shop.controller.Parameter.PRODUCT_ID;
import static by.matsuk.shop.controller.SessionAttribute.CURRENT_PAGE;

/**
 * The type Restore menu product command.
 */
public class RestoreMenuProductCommand implements Command {
    private final MenuService menuService = MenuServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        String currentPage = (String) session.getAttribute(CURRENT_PAGE);
        router.setRedirectType();
        router.setCurrentPage(currentPage);
        try {
            long postcardId = Long.parseLong(request.getParameter(PRODUCT_ID));
            menuService.restorePostcardsProductById(postcardId);
        } catch (ServiceException | NumberFormatException e) {
            throw new CommandException("Exception in a RestoreMenuProductCommand class ", e);
        }
        return router;
    }
}
