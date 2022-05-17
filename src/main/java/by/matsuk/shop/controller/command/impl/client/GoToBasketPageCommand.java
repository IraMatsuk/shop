package by.matsuk.shop.controller.command.impl.client;

import by.matsuk.shop.controller.Router;
import by.matsuk.shop.controller.command.Command;
import by.matsuk.shop.entity.Postcard;
import by.matsuk.shop.entity.User;
import by.matsuk.shop.entity.UserDiscount;
import by.matsuk.shop.exception.CommandException;
import by.matsuk.shop.exception.ServiceException;
import by.matsuk.shop.model.service.CalculateService;
import by.matsuk.shop.model.service.UserDiscountService;
import by.matsuk.shop.model.service.impl.UserDiscountServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static by.matsuk.shop.controller.Parameter.TOTAL_PRICE;
import static by.matsuk.shop.controller.PathPage.BASKET_PAGE;
import static by.matsuk.shop.controller.PathPage.ERROR_500;
import static by.matsuk.shop.controller.SessionAttribute.CART;
import static by.matsuk.shop.controller.SessionAttribute.USER;

/**
 * The type Go to basket page command.
 */
public class GoToBasketPageCommand implements Command {
    private final UserDiscountService service = UserDiscountServiceImpl.getInstance();
    private final CalculateService calculateService = CalculateService.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        Map<Postcard, Integer> productMap = (HashMap<Postcard, Integer>) session.getAttribute(CART);
        User user = (User) session.getAttribute(USER);

        router.setCurrentPage(request.getContextPath() + BASKET_PAGE);
        try {
            if (!productMap.isEmpty()) {
                Optional<UserDiscount> userDiscount = service.findDiscountById(user.getDiscountId());
                if (userDiscount.isPresent()) {
                    double totalPrice = calculateService.calculateTotalPrice(userDiscount.get(), productMap).doubleValue();
                    request.setAttribute(TOTAL_PRICE, totalPrice);
                } else {
                    router.setRedirectType();
                    router.setCurrentPage(ERROR_500);
                    return router;
                }
            }
        } catch (ServiceException e) {
            throw new CommandException("Exception in a GoToBasketPageCommand class", e);
        }
        return router;
    }
}
