package by.matsuk.shop.controller.command.impl.admin;

import by.matsuk.shop.controller.Router;
import by.matsuk.shop.controller.command.Command;
import by.matsuk.shop.exception.CommandException;
import by.matsuk.shop.exception.ServiceException;
import by.matsuk.shop.model.service.CatalogService;
import by.matsuk.shop.model.service.impl.CatalogServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static by.matsuk.shop.controller.Parameter.PRODUCT_ID;
import static by.matsuk.shop.controller.SessionAttribute.CURRENT_PAGE;

/**
 * The type Restore menu product command.
 */
public class RestorePostcardProductCommand implements Command {
    private final CatalogService catalogService = CatalogServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        String currentPage = (String) session.getAttribute(CURRENT_PAGE);
        router.setRedirectType();
        router.setCurrentPage(currentPage);
        try {
            long postcardId = Long.parseLong(request.getParameter(PRODUCT_ID));
            catalogService.restorePostcardsProductById(postcardId);
        } catch (ServiceException | NumberFormatException e) {
            throw new CommandException("Exception in a RestorePostcardProductCommand class ", e);
        }
        return router;
    }
}
