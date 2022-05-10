package by.matsuk.shop.controller.command.impl.admin;

import by.matsuk.shop.controller.Router;
import by.matsuk.shop.controller.command.Command;
import by.matsuk.shop.exception.CommandException;
import by.matsuk.shop.exception.ServiceException;
import by.matsuk.shop.model.service.impl.MenuServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static by.matsuk.shop.controller.Parameter.*;
import static by.matsuk.shop.controller.PropertiesKey.*;
import static by.matsuk.shop.controller.SessionAttribute.CURRENT_PAGE;

/**
 * The type Update product command.
 */
public class UpdateProductCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private final MenuServiceImpl service = MenuServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        Map<String,String> map = new HashMap<>();
        HttpSession session = request.getSession();
        String currentPage = (String) session.getAttribute(CURRENT_PAGE);
        map.put(PRODUCT_NAME, request.getParameter(PRODUCT_NAME));
        logger.info(request.getParameter(PRODUCT_NAME));
        map.put(PRODUCT_AUTHOR, request.getParameter(PRODUCT_AUTHOR));
        map.put(PRODUCT_DESCRIPTION, request.getParameter(PRODUCT_DESCRIPTION));
        map.put(PRODUCT_DISCOUNT, request.getParameter(PRODUCT_DISCOUNT));
        map.put(PRODUCT_PRICE, request. getParameter(PRODUCT_PRICE));
        map.put(PRODUCT_SECTION, request.getParameter(PRODUCT_SECTION));
        router.setCurrentPage(currentPage);
        try {
            long id = Long.parseLong(request.getParameter(PRODUCT_ID));
            if (service.updateProduct(id, map).isEmpty()) {
                for (String key : map.keySet()) {
                    String value = map.get(key);
                    switch (value) {
                        case INVALID_PRODUCT_COMPOSITION -> request.setAttribute(INVALID_PRODUCT_COMPOSITION, INVALID_PRODUCT_COMPOSITION_MESSAGE);
                        case NOT_UNIQ_PRODUCT_NAME -> request.setAttribute(NOT_UNIQ_PRODUCT_NAME, NOT_UNIQ_PRODUCT_NAME_MESSAGE);
                        case INVALID_PRODUCT_AUTHOR -> request.setAttribute(INVALID_PRODUCT_AUTHOR, INVALID_PRODUCT_AUTHOR_MESSAGE);
                        case INVALID_PRODUCT_DISCOUNT -> request.setAttribute(INVALID_PRODUCT_DISCOUNT, INVALID_PRODUCT_DISCOUNT_MESSAGE);
                        case INVALID_PRODUCT_NAME -> request.setAttribute(INVALID_PRODUCT_NAME, INVALID_PRODUCT_NAME_MESSAGE);
                        case INVALID_PRODUCT_PRICE -> request.setAttribute(INVALID_PRODUCT_PRICE, INVALID_PRODUCT_PRICE_MESSAGE);
                        case INVALID_PRODUCT_DESCRIPTION -> request.setAttribute(INVALID_PRODUCT_DESCRIPTION, INVALID_PRODUCT_DESCRIPTION_MESSAGE);
                        case INVALID_PRODUCT_SECTION -> request.setAttribute(INVALID_PRODUCT_SECTION, INVALID_PRODUCT_SECTION_MESSAGE);
                    }
                }
                return router;
            }
            router.setRedirectType();
        } catch (ServiceException | NumberFormatException e) {
            throw new CommandException("Exception in a UpdateProductCommand class ", e);
        }
        return router;
    }
}