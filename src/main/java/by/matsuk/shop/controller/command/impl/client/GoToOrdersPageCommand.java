package by.matsuk.shop.controller.command.impl.client;

import by.matsuk.shop.controller.Router;
import by.matsuk.shop.controller.command.Command;
import by.matsuk.shop.entity.Order;
import by.matsuk.shop.entity.User;
import by.matsuk.shop.exception.CommandException;
import by.matsuk.shop.exception.ServiceException;
import by.matsuk.shop.model.service.OrderService;
import by.matsuk.shop.model.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static by.matsuk.shop.controller.Parameter.ORDER_LIST;
import static by.matsuk.shop.controller.PathPage.ORDERS_PAGE;
import static by.matsuk.shop.controller.SessionAttribute.USER;

/**
 * The type Go to orders page command.
 */
public class GoToOrdersPageCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private final OrderService service = OrderServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        router.setCurrentPage(ORDERS_PAGE);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        try {
            logger.info("User: " + user);
            List<Order> listOrder = service.findAllUserOrders(user);
            logger.info("List order: " + listOrder);
            request.setAttribute(ORDER_LIST, listOrder);
        } catch (ServiceException e) {
            throw new CommandException("Exception in a GoToOrdersPageCommand class. ", e);
        }
        return router;
    }
}