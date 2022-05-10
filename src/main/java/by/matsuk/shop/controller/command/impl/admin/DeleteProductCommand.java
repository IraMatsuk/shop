package by.matsuk.shop.controller.command.impl.admin;

import by.matsuk.shop.controller.Router;
import by.matsuk.shop.controller.command.Command;
import by.matsuk.shop.exception.CommandException;
import by.matsuk.shop.exception.ServiceException;
import by.matsuk.shop.model.service.MenuService;
import by.matsuk.shop.model.service.impl.MenuServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.matsuk.shop.controller.Parameter.PRODUCT_ID;
import static by.matsuk.shop.controller.SessionAttribute.CURRENT_PAGE;

/**
 * The type Delete product command. It is used a safe removal.
 */
public class DeleteProductCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private final MenuService service = MenuServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        try {
            long id = Long.parseLong(request.getParameter(PRODUCT_ID));
            service.deleteProductById(id);
            HttpSession session = request.getSession();
            String currentPage = (String) session.getAttribute(CURRENT_PAGE);
            logger.info("Current page after delete product - " + currentPage);
            session.setAttribute(CURRENT_PAGE, currentPage);
            router.setCurrentPage(currentPage);
        } catch (ServiceException | NumberFormatException e) {
            throw new CommandException("Exception in a DeleteProductCommand class ", e);
        }
        return router;
    }
}