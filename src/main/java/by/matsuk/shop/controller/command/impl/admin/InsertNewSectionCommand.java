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

import java.util.List;

import static by.matsuk.shop.controller.Parameter.*;
import static by.matsuk.shop.controller.PropertiesKey.INVALID_SECTION_NAME_MESSAGE;
import static by.matsuk.shop.controller.PropertiesKey.NOT_UNIQ_SECTION_NAME_MESSAGE;
import static by.matsuk.shop.controller.SessionAttribute.CURRENT_PAGE;
import static by.matsuk.shop.controller.SessionAttribute.SECTION_LIST;

/**
 * The type Insert new section command.
 */
public class InsertNewSectionCommand implements Command {
    private final SectionService service = SectionServiceImpl.getInstance();
    private final Validator validator = ValidatorImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        ServletContext context = request.getServletContext();
        String sectionName = request.getParameter(SECTION_NAME);
        String currentPage = (String) session.getAttribute(CURRENT_PAGE);
        router.setCurrentPage(currentPage);
        try {
            if (!validator.isCorrectSectionName(sectionName)) {
                request.setAttribute(INVALID_SECTION_NAME, INVALID_SECTION_NAME_MESSAGE);
                return router;
            }

            if (service.findSectionByName(sectionName).isPresent()) {
                request.setAttribute(NOT_UNIQ_SECTION_NAME, NOT_UNIQ_SECTION_NAME_MESSAGE);
                return router;
            }

            service.addNewSection(sectionName);
            List<Section> sectionList = service.findAllSections();
            if (!sectionList.isEmpty()) {
                context.setAttribute(SECTION_LIST, sectionList);
            }

            router.setRedirectType();
        } catch (ServiceException e) {
            throw new CommandException("Exception in a InsertNewSectionCommand class. ", e);
        }
        return router;
    }
}