package by.matsuk.shop.controller.command.impl.admin;

import by.matsuk.shop.controller.Router;
import by.matsuk.shop.controller.command.Command;
import by.matsuk.shop.exception.CommandException;
import by.matsuk.shop.exception.ServiceException;
import by.matsuk.shop.model.service.CatalogService;
import by.matsuk.shop.model.service.impl.CatalogServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static by.matsuk.shop.controller.Parameter.*;
import static by.matsuk.shop.controller.PathPage.ERROR_500;
import static by.matsuk.shop.controller.SessionAttribute.CURRENT_PAGE;

/**
 * The type Upload product photo command.
 */
public class UploadProductPhotoCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String RELATIVE_PATH = "picture/";
    private final CatalogService service = CatalogServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        try {
            String submittedFileName = request.getPart(PICTURE_PATH).getSubmittedFileName();
            String path = RELATIVE_PATH + submittedFileName;
            String name = request.getParameter(PRODUCT_NAME);
            logger.info("Update photo is successful " + path);
            if (!service.updateProductPhoto(path, name)) {
                logger.info("Update product photo is failed");
                router.setCurrentPage(ERROR_500);
                return router;
            }
        } catch (IOException | ServletException | ServiceException e) {
            throw new CommandException("Upload photo failed ", e);
        }
        HttpSession session = request.getSession();
        String current_page = (String) session.getAttribute(CURRENT_PAGE);
        logger.info("Upload photo current page is " + current_page);
        router.setCurrentPage(current_page);
        return router;
    }
}