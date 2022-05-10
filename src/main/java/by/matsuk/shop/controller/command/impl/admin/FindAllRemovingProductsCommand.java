package by.matsuk.shop.controller.command.impl.admin;

import by.matsuk.shop.controller.Router;
import by.matsuk.shop.controller.command.Command;
import by.matsuk.shop.entity.Postcard;
import by.matsuk.shop.exception.CommandException;
import by.matsuk.shop.exception.ServiceException;
import by.matsuk.shop.model.service.MenuService;
import by.matsuk.shop.model.service.impl.MenuServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.List;

import static by.matsuk.shop.controller.Parameter.POSTCARD_LIST;
import static by.matsuk.shop.controller.Parameter.RESTORE_MENU;
import static by.matsuk.shop.controller.PathPage.RESTORE_PAGE;

/**
 * The type Find all removing products command.
 */
public class FindAllRemovingProductsCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private final MenuService menuService = MenuServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        router.setCurrentPage(RESTORE_PAGE);
        try {
            List<Postcard> menuList = menuService.findAllRemovingPostcards();
            logger.info("Removing products: " + menuList);
            request.setAttribute(POSTCARD_LIST, menuList);
            request.setAttribute(RESTORE_MENU, true);
        } catch (ServiceException e) {
            throw new CommandException("Exception in a FindAllRemovingProductsCommand class ", e);
        }
        return router;
    }
}
