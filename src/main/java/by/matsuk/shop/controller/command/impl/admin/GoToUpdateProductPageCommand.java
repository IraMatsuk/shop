package by.matsuk.shop.controller.command.impl.admin;

import by.matsuk.shop.controller.Router;
import by.matsuk.shop.controller.command.Command;
import by.matsuk.shop.entity.Postcard;
import by.matsuk.shop.exception.CommandException;
import by.matsuk.shop.exception.ServiceException;
import by.matsuk.shop.model.service.CatalogService;
import by.matsuk.shop.model.service.impl.CatalogServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

import static by.matsuk.shop.controller.Parameter.PRODUCT_ID;
import static by.matsuk.shop.controller.Parameter.PRODUCT_MENU;
import static by.matsuk.shop.controller.PathPage.ERROR_500;
import static by.matsuk.shop.controller.PathPage.UPDATE_PRODUCT_PAGE;

/**
 * The type Go to update product page command.
 */
public class GoToUpdateProductPageCommand implements Command {
    private final CatalogService service = CatalogServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        try {
            long postcardId = Long.parseLong(request.getParameter(PRODUCT_ID));
            Optional<Postcard> catalog = service.findProductById(postcardId);
            if (!catalog.isEmpty()) {
                request.setAttribute(PRODUCT_MENU, catalog.get());
                request.setAttribute(PRODUCT_ID, postcardId);
                router.setCurrentPage(UPDATE_PRODUCT_PAGE);
            } else {
                router.setRedirectType();
                router.setCurrentPage(ERROR_500);
            }
        } catch (ServiceException | NumberFormatException e) {
            throw new CommandException("Exception in a GoToUpdateProductPageCommand class", e);
        }
        return router;
    }
}
