package by.matsuk.shop.controller.command.impl.admin;

import by.matsuk.shop.controller.Router;
import by.matsuk.shop.controller.command.Command;
import by.matsuk.shop.entity.Order;
import by.matsuk.shop.exception.CommandException;
import by.matsuk.shop.exception.ServiceException;
import by.matsuk.shop.model.service.OrderService;
import by.matsuk.shop.model.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static by.matsuk.shop.controller.Parameter.ORDER_ID;
import static by.matsuk.shop.controller.Parameter.STATE;
import static by.matsuk.shop.controller.SessionAttribute.CURRENT_PAGE;

/**
 * The type Change order state command.
 */
public class ChangeOrderStateCommand implements Command {
    private final OrderService service = OrderServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        String currentPage = (String) session.getAttribute(CURRENT_PAGE);
        router.setCurrentPage(request.getContextPath() + currentPage);
        String state = request.getParameter(STATE);
        Order.OrderState orderState = Order.OrderState.valueOf(state);
        try {
            long id = Long.parseLong(request.getParameter(ORDER_ID));
            service.changeOrderStateById(id, orderState);
            router.setRedirectType();
        } catch (ServiceException | NumberFormatException e) {
            throw new CommandException("Exception in a ChangeOrderStateCommand class. ", e);
        }
        return router;
    }
}