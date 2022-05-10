package by.matsuk.shop.controller.command.impl.admin;

import by.matsuk.shop.controller.Router;
import by.matsuk.shop.controller.command.Command;
import by.matsuk.shop.entity.Section;
import by.matsuk.shop.exception.CommandException;
import by.matsuk.shop.exception.ServiceException;
import by.matsuk.shop.model.service.SectionService;
import by.matsuk.shop.model.service.impl.SectionServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import static by.matsuk.shop.controller.Parameter.RESTORE_SECTION;
import static by.matsuk.shop.controller.PathPage.RESTORE_PAGE;
import static by.matsuk.shop.controller.SessionAttribute.SECTION_LIST;

/**
 * The type Find all removing sections command.
 */
public class FindAllRemovingSectionsCommand implements Command {
    private final SectionService sectionService = SectionServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        router.setCurrentPage(RESTORE_PAGE);
        try {
            List<Section> sectionList = sectionService.findAllRemovingSections();
            request.setAttribute(SECTION_LIST, sectionList);
            request.setAttribute(RESTORE_SECTION, true);
        } catch (ServiceException e) {
            throw new CommandException("Exception in a FindAllRemovingSectionsCommand class ", e);
        }
        return router;
    }
}
