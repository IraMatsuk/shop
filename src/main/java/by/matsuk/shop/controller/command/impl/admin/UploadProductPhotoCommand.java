package by.matsuk.shop.controller.command.impl.admin;

import by.matsuk.shop.controller.Router;
import by.matsuk.shop.controller.command.Command;
import by.matsuk.shop.exception.CommandException;
import by.matsuk.shop.exception.ServiceException;
import by.matsuk.shop.model.service.MenuService;
import by.matsuk.shop.model.service.impl.MenuServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import static by.matsuk.shop.controller.Parameter.PICTURE_PATH;
import static by.matsuk.shop.controller.Parameter.PRODUCT_NAME;
import static by.matsuk.shop.controller.PathPage.ERROR_500;
import static by.matsuk.shop.controller.SessionAttribute.CURRENT_PAGE;

/**
 * The type Upload product photo command.
 */
public class UploadProductPhotoCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String ABSOLUTE_PATH = "C:/Users/admin/source/picture/"; //TODO!!!!
    private final MenuService service = MenuServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        try (InputStream inputStream = request.getPart(PICTURE_PATH).getInputStream()){
            String submittedFileName = request.getPart(PICTURE_PATH).getSubmittedFileName();
            String path = ABSOLUTE_PATH + submittedFileName;
            Path imagePath = new File(path).toPath();
            long bytes = Files.copy(
                    inputStream,
                    imagePath,
                    StandardCopyOption.REPLACE_EXISTING);
            logger.info("Upload result is successfully " + bytes + " " + path);
            String name = request.getParameter(PRODUCT_NAME);

            if(!service.updateProductPhoto(path, name)){
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