package by.matsuk.shop.controller.command.impl.admin;

import by.matsuk.shop.controller.Router;
import by.matsuk.shop.controller.command.Command;
import by.matsuk.shop.entity.Section;
import by.matsuk.shop.exception.CommandException;
import by.matsuk.shop.exception.ServiceException;
import by.matsuk.shop.model.service.SectionService;
import by.matsuk.shop.model.service.impl.SectionServiceImpl;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static by.matsuk.shop.controller.Parameter.SECTION_ID;
import static by.matsuk.shop.controller.SessionAttribute.CURRENT_PAGE;
import static by.matsuk.shop.controller.SessionAttribute.SECTION_LIST;

/**
 * The type Restore section command.
 */
public class RestoreSectionCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private final SectionService sectionService = SectionServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        router.setRedirectType();
        HttpSession session = request.getSession();
        String currentPage = (String) session.getAttribute(CURRENT_PAGE);
        router.setCurrentPage(request.getContextPath() + currentPage);
        try {
            logger.info(request.getParameter(SECTION_ID));
            long sectionId = Long.parseLong(request.getParameter(SECTION_ID));
            logger.info("Section ID = " + sectionId);
            if (sectionService.restoreSectionById(sectionId)) {
                ServletContext context = request.getServletContext();
                List<Section> sectionList = sectionService.findAllSections();
                logger.info("Section list: " + sectionList);
                context.setAttribute(SECTION_LIST, sectionList);
            }
        } catch (ServiceException e) {
            throw new CommandException("Exception in a RestoreSectionCommand class ", e);
        }
        return router;
    }
}
