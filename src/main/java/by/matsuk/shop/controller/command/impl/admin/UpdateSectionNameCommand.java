package by.matsuk.shop.controller.command.impl.admin;

import by.matsuk.shop.controller.Router;
import by.matsuk.shop.controller.command.Command;
import by.matsuk.shop.entity.Section;
import by.matsuk.shop.exception.CommandException;
import by.matsuk.shop.exception.ServiceException;
import by.matsuk.shop.model.service.SectionService;
import by.matsuk.shop.model.service.impl.SectionServiceImpl;
import by.matsuk.shop.validator.Validator;
import by.matsuk.shop.validator.impl.ValidatorImpl;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

import static by.matsuk.shop.controller.Parameter.*;
import static by.matsuk.shop.controller.PathPage.ERROR_500;
import static by.matsuk.shop.controller.PropertiesKey.*;
import static by.matsuk.shop.controller.SessionAttribute.CURRENT_PAGE;
import static by.matsuk.shop.controller.SessionAttribute.SECTION_LIST;

/**
 * The type Update section name command.
 */
public class UpdateSectionNameCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private final SectionService service = SectionServiceImpl.getInstance();
    private final Validator validator = ValidatorImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        ServletContext context = request.getServletContext();
        String currentPage = (String) session.getAttribute(CURRENT_PAGE);
        String sectionName = request.getParameter(NEW_SECTION_NAME);
        router.setCurrentPage(currentPage);
        try {
            boolean result = true;
            if (request.getParameter(PRODUCT_SECTION) == null) {
                request.setAttribute(INVALID_PRODUCT_SECTION, INVALID_PRODUCT_SECTION_MESSAGE);
                result = false;
            }

            if (!validator.isCorrectSectionName(sectionName)) {
                request.setAttribute(INVALID_NEW_SECTION_NAME, INVALID_SECTION_NAME_MESSAGE);
                result = false;
            }

            if (service.findSectionByName(sectionName).isPresent()) {
                Section findSection = service.findSectionByName(sectionName).get();
                if (!findSection.getSectionName().equals(sectionName)) {
                    request.setAttribute(NOT_UNIQ_NEW_SECTION_NAME, NOT_UNIQ_SECTION_NAME_MESSAGE);
                    result = false;
                }
            }

            if (!result) {
                return router;
            }
            long sectionId = Long.parseLong(request.getParameter(PRODUCT_SECTION));
            Optional<Section> oldSection = service.updateSectionName(new Section(sectionId, sectionName, true));
            router.setRedirectType();
            if (oldSection.isPresent()) {
                List<Section> sectionList = service.findAllSections();
                context.setAttribute(SECTION_LIST, sectionList);
            } else {
                logger.warn("Incorrect update section name. Section id = " + sectionId);
                router.setCurrentPage(ERROR_500);
                return router;
            }
        } catch (ServiceException | NumberFormatException e) {
            throw new CommandException("Exception in a UpdateSectionNameCommand class. ", e);
        }
        return router;
    }
}