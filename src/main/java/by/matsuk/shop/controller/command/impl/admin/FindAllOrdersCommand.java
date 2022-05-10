package by.matsuk.shop.controller.command.impl.admin;

import by.matsuk.shop.controller.Router;
import by.matsuk.shop.controller.command.Command;
import by.matsuk.shop.entity.CompleteOrder;
import by.matsuk.shop.exception.CommandException;
import by.matsuk.shop.exception.ServiceException;
import by.matsuk.shop.model.service.OrderService;
import by.matsuk.shop.model.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import static by.matsuk.shop.controller.Parameter.ORDER_LIST;
import static by.matsuk.shop.controller.PathPage.ORDERS_PAGE;

/**
 * The type Find all orders command.
 */
public class FindAllOrdersCommand implements Command {
    private final OrderService orderService = OrderServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        router.setCurrentPage(ORDERS_PAGE);
        try {
            List<CompleteOrder> orderList = orderService.findAllOrders();
            request.setAttribute(ORDER_LIST, orderList);
        } catch (ServiceException e) {
            throw new CommandException("Exception in a FindAllOrdersCommand class.", e);
        }
        return router;
    }
}
