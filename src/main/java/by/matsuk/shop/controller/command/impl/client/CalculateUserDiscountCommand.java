package by.matsuk.shop.controller.command.impl.client;

import by.matsuk.shop.controller.Router;
import by.matsuk.shop.controller.command.Command;
import by.matsuk.shop.entity.User;
import by.matsuk.shop.entity.UserDiscount;
import by.matsuk.shop.exception.CommandException;
import by.matsuk.shop.exception.ServiceException;
import by.matsuk.shop.model.service.OrderService;
import by.matsuk.shop.model.service.UserDiscountService;
import by.matsuk.shop.model.service.impl.OrderServiceImpl;
import by.matsuk.shop.model.service.impl.UserDiscountServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

import static by.matsuk.shop.controller.Parameter.USER_DISCOUNT;
import static by.matsuk.shop.controller.Parameter.USER_ORDERS_FOR_THE_YEAR;
import static by.matsuk.shop.controller.PathPage.DISCOUNT_PAGE;
import static by.matsuk.shop.controller.SessionAttribute.USER;

/**
 * The type Calculate user discount command.
 */
public class CalculateUserDiscountCommand implements Command {
    private final UserDiscountService discountService = UserDiscountServiceImpl.getInstance();
    private final OrderService orderService = OrderServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        try {
            Optional<UserDiscount> userDiscount = discountService.findDiscountById(user.getDiscountId());
            userDiscount.ifPresent(discount -> request.setAttribute(USER_DISCOUNT, discount.getDiscount()));

            int numberUserOrders = orderService.calculateOrdersNumberPerYear(user.getUserId());
            request.setAttribute(USER_ORDERS_FOR_THE_YEAR, numberUserOrders);
            router.setCurrentPage(DISCOUNT_PAGE);
        } catch (ServiceException e) {
            throw new CommandException("Exception in a CalculateUserDiscountCommand class. ", e);
        }
        return router;
    }
}
