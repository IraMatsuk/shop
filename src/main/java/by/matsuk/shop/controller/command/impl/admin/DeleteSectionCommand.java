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

import java.util.List;

import static by.matsuk.shop.controller.Parameter.INVALID_DELETE_PRODUCT_SECTION;
import static by.matsuk.shop.controller.Parameter.PRODUCT_SECTION;
import static by.matsuk.shop.controller.PropertiesKey.INVALID_PRODUCT_SECTION_MESSAGE;
import static by.matsuk.shop.controller.SessionAttribute.CURRENT_PAGE;
import static by.matsuk.shop.controller.SessionAttribute.SECTION_LIST;

/**
 * The type Delete section command. It is used a safe removal.
 */
public class DeleteSectionCommand implements Command {
    private final SectionService service = SectionServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        try {
            String currentPage = (String) session.getAttribute(CURRENT_PAGE);
            router.setCurrentPage(currentPage);
            if (request.getParameter(PRODUCT_SECTION) != null) {
                long sectionId = Long.parseLong(request.getParameter(PRODUCT_SECTION));
                service.deleteSectionById(sectionId);
                List<Section> listSection = service.findAllSections();
                ServletContext context = request.getServletContext();
                context.setAttribute(SECTION_LIST, listSection);
            } else {
                request.setAttribute(INVALID_DELETE_PRODUCT_SECTION, INVALID_PRODUCT_SECTION_MESSAGE);
            }
        } catch (ServiceException | NumberFormatException e) {
            throw new CommandException("Exception in a DeleteSectionCommand class. ", e);
        }
        return router;
    }
}
