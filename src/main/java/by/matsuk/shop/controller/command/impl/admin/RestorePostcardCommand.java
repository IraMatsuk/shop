package by.matsuk.shop.controller.command.impl.admin;

import by.matsuk.shop.controller.Router;
import by.matsuk.shop.controller.command.Command;
import by.matsuk.shop.entity.Postcard;
import by.matsuk.shop.exception.CommandException;
import by.matsuk.shop.exception.ServiceException;
import by.matsuk.shop.model.service.CatalogService;
import by.matsuk.shop.model.service.impl.CatalogServiceImpl;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static by.matsuk.shop.controller.Parameter.POSTCARD_LIST;
import static by.matsuk.shop.controller.Parameter.PRODUCT_ID;
import static by.matsuk.shop.controller.SessionAttribute.CURRENT_PAGE;

/**
 * The type Restore postcard command.
 */
public class RestorePostcardCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private final CatalogService catalogService = CatalogServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        String currentPage = (String) session.getAttribute(CURRENT_PAGE);
        router.setRedirectType();
        router.setCurrentPage(currentPage);
        try {
            logger.info(request.getParameter(PRODUCT_ID));
            long postcardId = Long.parseLong(request.getParameter(PRODUCT_ID));
            logger.info("Section ID = " + postcardId);
            if (catalogService.restorePostcardsProductById(postcardId)) {
                ServletContext context = request.getServletContext();
                List<Postcard> postcardList = catalogService.findAllRemovingPostcards();
                logger.info("Postcard list: " + postcardList);
                context.setAttribute(POSTCARD_LIST, postcardList);
            }
        } catch (ServiceException | NumberFormatException e) {
            throw new CommandException("Exception in a RestorePostcardProductCommand class ", e);
        }
        return router;
    }
}
