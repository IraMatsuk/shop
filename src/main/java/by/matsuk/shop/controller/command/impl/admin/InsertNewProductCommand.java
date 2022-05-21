package by.matsuk.shop.controller.command.impl.admin;

import by.matsuk.shop.controller.Router;
import by.matsuk.shop.controller.command.Command;
import by.matsuk.shop.exception.CommandException;
import by.matsuk.shop.exception.ServiceException;
import by.matsuk.shop.model.service.impl.MenuServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

import static by.matsuk.shop.controller.Parameter.*;
import static by.matsuk.shop.controller.PathPage.ADD_MENU_PAGE;
import static by.matsuk.shop.controller.PropertiesKey.*;

/**
 * The type Insert new product command.
 */
public class InsertNewProductCommand implements Command {
    private static final String DEFAULT_IMAGE = "picture/default_image.png";
    private final MenuServiceImpl service = MenuServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        Map<String, String> map = new HashMap<>();
        map.put(PRODUCT_NAME, request.getParameter(PRODUCT_NAME));
        map.put(PRODUCT_AUTHOR, request.getParameter(PRODUCT_AUTHOR));
        map.put(PRODUCT_DESCRIPTION, request.getParameter(PRODUCT_DESCRIPTION));
        map.put(PRODUCT_DISCOUNT, request.getParameter(PRODUCT_DISCOUNT));
        map.put(PRODUCT_PRICE, request.getParameter(PRODUCT_PRICE));
        map.put(PRODUCT_SECTION, request.getParameter(PRODUCT_SECTION));
        router.setCurrentPage(request.getContextPath() + ADD_MENU_PAGE);
        try {
            if (service.addNewProduct(map, DEFAULT_IMAGE)) {
                router.setRedirectType();
                return router;
            }
            for (String key : map.keySet()) {
                String value = map.get(key);
                switch (value) {
                    case NOT_UNIQ_PRODUCT_NAME -> request.setAttribute(NOT_UNIQ_PRODUCT_NAME, NOT_UNIQ_PRODUCT_NAME_MESSAGE);
                    case INVALID_PRODUCT_AUTHOR -> request.setAttribute(INVALID_PRODUCT_AUTHOR, INVALID_PRODUCT_AUTHOR_MESSAGE);
                    case INVALID_PRODUCT_DESCRIPTION -> request.setAttribute(INVALID_PRODUCT_DESCRIPTION, INVALID_PRODUCT_DESCRIPTION_MESSAGE);
                    case INVALID_PRODUCT_DISCOUNT -> request.setAttribute(INVALID_PRODUCT_DISCOUNT, INVALID_PRODUCT_DISCOUNT_MESSAGE);
                    case INVALID_PRODUCT_NAME -> request.setAttribute(INVALID_PRODUCT_NAME, INVALID_PRODUCT_NAME_MESSAGE);
                    case INVALID_PRODUCT_PRICE -> request.setAttribute(INVALID_PRODUCT_PRICE, INVALID_PRODUCT_PRICE_MESSAGE);
                    case INVALID_PRODUCT_SECTION -> request.setAttribute(INVALID_PRODUCT_SECTION, INVALID_PRODUCT_SECTION_MESSAGE);
                }
            }
        } catch (ServiceException e) {
            throw new CommandException("Exception in a InsertNewProductCommand class ", e);
        }
        return router;
    }
}
