package by.matsuk.shop.controller.command.impl.client;

import by.matsuk.shop.controller.Router;
import by.matsuk.shop.controller.command.Command;
import by.matsuk.shop.entity.Postcard;
import by.matsuk.shop.exception.CommandException;
import by.matsuk.shop.exception.ServiceException;
import by.matsuk.shop.model.service.CatalogService;
import by.matsuk.shop.model.service.impl.CatalogServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;

import static by.matsuk.shop.controller.Parameter.PRODUCT_NUMBER;
import static by.matsuk.shop.controller.Parameter.SELECTED;
import static by.matsuk.shop.controller.SessionAttribute.CART;
import static by.matsuk.shop.controller.SessionAttribute.CURRENT_PAGE;

/**
 * The type Add product to cart command.
 */
public class AddProductToCartCommand implements Command {
    private final CatalogService service = CatalogServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String currentPage = (String) session.getAttribute(CURRENT_PAGE);
        Map<Postcard, Integer> productMap = (HashMap<Postcard, Integer>) session.getAttribute(CART);
        int productNumber = Integer.parseInt(request.getParameter(PRODUCT_NUMBER));
        Router router = new Router();
        try {
            if(productNumber >= 1) {
                long id = Long.parseLong(request.getParameter(SELECTED));
                service.addProductToBasket(productMap, id, productNumber);
                session.setAttribute(CART, productMap);
            }
            router.setRedirectType();
            router.setCurrentPage(currentPage);
        } catch (ServiceException | NumberFormatException e) {
            throw new CommandException("Exception in a AddProductToCartCommand class ", e);
        }
        return router;
    }
}