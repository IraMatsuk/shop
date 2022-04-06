package by.matsuk.shop.controller.command.impl.admin;

import by.matsuk.shop.controller.Router;
import by.matsuk.shop.controller.command.Command;
import by.matsuk.shop.exception.CommandException;
import by.matsuk.shop.exception.ServiceException;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

import static by.matsuk.shop.controller.Parameter.*;
import static by.matsuk.shop.controller.PathPage.ADD_MENU_PAGE;
import static by.matsuk.shop.controller.PropertiesKey.*;

public class InsertNewProductCommand implements Command {
    private static final String DEFAULT_IMAGE = "picture/default-image_1920.png";
    private final MenuServiceImpl service = MenuServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        Map<String,String> map = new HashMap<>();
        map.put(PRODUCT_NAME, request.getParameter(PRODUCT_NAME));
        map.put(PRODUCT_COMPOSITION, request.getParameter(PRODUCT_COMPOSITION));
        map.put(PRODUCT_WEIGHT, request.getParameter(PRODUCT_WEIGHT));
        map.put(PRODUCT_CALORIES, request.getParameter(PRODUCT_CALORIES));
        map.put(PRODUCT_TIME, request.getParameter(PRODUCT_TIME));
        map.put(PRODUCT_DISCOUNT, request.getParameter(PRODUCT_DISCOUNT));
        map.put(PRODUCT_PRICE, request. getParameter(PRODUCT_PRICE));
        map.put(PRODUCT_SECTION, request.getParameter(PRODUCT_SECTION));
        router.setCurrentPage(ADD_MENU_PAGE);
        try {
            if (service.addNewProduct(map, DEFAULT_IMAGE)) {
                router.setRedirectType();
                return router;
            }
            for (String key : map.keySet()) {
                String value = map.get(key);
                switch (value) {
                    case INVALID_PRODUCT_COMPOSITION -> request.setAttribute(INVALID_PRODUCT_COMPOSITION, INVALID_PRODUCT_COMPOSITION_MESSAGE);
                    case NOT_UNIQ_PRODUCT_NAME -> request.setAttribute(NOT_UNIQ_PRODUCT_NAME, NOT_UNIQ_PRODUCT_NAME_MESSAGE);
                    case INVALID_PRODUCT_CALORIES -> request.setAttribute(INVALID_PRODUCT_CALORIES, INVALID_PRODUCT_CALORIES_MESSAGE);
                    case INVALID_PRODUCT_DISCOUNT -> request.setAttribute(INVALID_PRODUCT_DISCOUNT, INVALID_PRODUCT_DISCOUNT_MESSAGE);
                    case INVALID_PRODUCT_NAME -> request.setAttribute(INVALID_PRODUCT_NAME, INVALID_PRODUCT_NAME_MESSAGE);
                    case INVALID_PRODUCT_PRICE -> request.setAttribute(INVALID_PRODUCT_PRICE, INVALID_PRODUCT_PRICE_MESSAGE);
                    case INVALID_PRODUCT_WEIGHT -> request.setAttribute(INVALID_PRODUCT_WEIGHT, INVALID_PRODUCT_WEIGHT_MESSAGE);
                    case INVALID_COOKING_TIME -> request.setAttribute(INVALID_COOKING_TIME, INVALID_COOKING_TIME_MESSAGE);
                    case INVALID_PRODUCT_SECTION -> request.setAttribute(INVALID_PRODUCT_SECTION, INVALID_PRODUCT_SECTION_MESSAGE);
                }
            }
        } catch (ServiceException e) {
            throw new CommandException("Exception in a InsertNewProductCommand class ", e);
        }
        return router;
    }
}
