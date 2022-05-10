package by.matsuk.shop.controller.command.impl.client;

import by.matsuk.shop.controller.Router;
import by.matsuk.shop.controller.command.Command;
import by.matsuk.shop.entity.Postcard;
import by.matsuk.shop.exception.CommandException;
import by.matsuk.shop.exception.ServiceException;
import by.matsuk.shop.model.service.MenuService;
import by.matsuk.shop.model.service.impl.MenuServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;

import static by.matsuk.shop.controller.Parameter.PRODUCT_ID;
import static by.matsuk.shop.controller.SessionAttribute.CART;
import static by.matsuk.shop.controller.SessionAttribute.CURRENT_PAGE;

/**
 * The type Delete product in basket command.
 */
public class DeleteProductInBasketCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private final MenuService service = MenuServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        String currentPage = (String) session.getAttribute(CURRENT_PAGE);
        Map<Postcard, Integer> mapMenu = (HashMap<Postcard, Integer>) session.getAttribute(CART);
        try {
            long id = Long.parseLong(request.getParameter(PRODUCT_ID));
            if (service.deleteProductFromBasket(mapMenu, id)) {
                session.setAttribute(CART, mapMenu);
            } else {
                logger.warn("Can't delete product from basket. ");
            }
        } catch (ServiceException | NumberFormatException e) {
            throw new CommandException("Exception in a DeleteProductInBasketCommand class ", e);
        }
        router.setCurrentPage(currentPage);
        return router;
    }
}
