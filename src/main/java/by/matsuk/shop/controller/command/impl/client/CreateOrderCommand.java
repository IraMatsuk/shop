package by.matsuk.shop.controller.command.impl.client;

import by.matsuk.shop.controller.Router;
import by.matsuk.shop.controller.command.Command;
import by.matsuk.shop.entity.Postcard;
import by.matsuk.shop.entity.User;
import by.matsuk.shop.exception.CommandException;
import by.matsuk.shop.exception.ServiceException;
import by.matsuk.shop.model.service.OrderService;
import by.matsuk.shop.model.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static by.matsuk.shop.controller.Parameter.*;
import static by.matsuk.shop.controller.PathPage.SUCCESS_PAGE;
import static by.matsuk.shop.controller.PropertiesKey.*;
import static by.matsuk.shop.controller.SessionAttribute.*;

/**
 * The type Create order command.
 */
public class CreateOrderCommand implements Command {
    private final OrderService service = OrderServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        Map<Postcard, Integer> orderProduct = (HashMap<Postcard, Integer>) session.getAttribute(CART);
        Map<String, String> orderInfo = new HashMap<>();

        orderInfo.put(ADDRESS, request.getParameter(ADDRESS));
        orderInfo.put(PRODUCT_PAYMENT, request.getParameter(PRODUCT_PAYMENT));
        try {
            double price = Double.parseDouble(request.getParameter(TOTAL_PRICE));
            BigDecimal totalCost = BigDecimal.valueOf(price);
            if (service.createOrder(orderProduct, orderInfo, user, totalCost)) {
                router.setCurrentPage(SUCCESS_PAGE);
                router.setRedirectType();
                orderProduct.clear();
                session.setAttribute(CART, orderProduct);
                return router;
            } else {
                String currentPage = (String) session.getAttribute(CURRENT_PAGE);
                router.setCurrentPage(currentPage);
                for (String key : orderInfo.keySet()) {
                    String value = orderInfo.get(key);
                    switch (value) { //TODO!
                        case INVALID_ORDER_ADDRESS -> request.setAttribute(INVALID_ORDER_ADDRESS, INVALID_ORDER_ADDRESS_MESSAGE);
                    }
                }
            }
        } catch (ServiceException | NumberFormatException e) {
            throw new CommandException("Exception in a CreateOrderCommand class. ", e);
        }
        return router;
    }
}