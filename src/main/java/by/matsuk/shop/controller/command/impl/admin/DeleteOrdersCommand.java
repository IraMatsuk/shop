package by.matsuk.shop.controller.command.impl.admin;

import by.matsuk.shop.controller.Router;
import by.matsuk.shop.controller.command.Command;
import by.matsuk.shop.exception.CommandException;
import by.matsuk.shop.exception.ServiceException;
import by.matsuk.shop.model.service.OrderService;
import by.matsuk.shop.model.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static by.matsuk.shop.controller.SessionAttribute.CURRENT_PAGE;

/**
 * The type Delete orders command. Delete orders that older than last year orders.
 */
public class DeleteOrdersCommand implements Command {
    private final OrderService service = OrderServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        String currentPage = (String) session.getAttribute(CURRENT_PAGE);
        router.setCurrentPage(currentPage);
        try {
            service.deleteOldOrders();
        } catch (ServiceException e) {
            throw new CommandException("Exception in a DeleteOrdersCommand class. ", e);
        }
        return router;
    }
}
