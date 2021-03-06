package by.matsuk.shop.controller.command.impl.common;

import by.matsuk.shop.controller.Router;
import by.matsuk.shop.controller.command.Command;
import by.matsuk.shop.entity.Postcard;
import by.matsuk.shop.exception.CommandException;
import by.matsuk.shop.exception.ServiceException;
import by.matsuk.shop.model.service.CatalogService;
import by.matsuk.shop.model.service.PaginationService;
import by.matsuk.shop.model.service.impl.CatalogServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import static by.matsuk.shop.controller.Parameter.*;
import static by.matsuk.shop.controller.PathPage.MENU_PAGE;

/**
 * The type Find all postcard by section command.
 */
public class FindAllPostcardBySectionCommand implements Command {
    private static final int PAGE_SIZE = 4;
    private final CatalogService catalogService = CatalogServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        try {
            long sectionId = Long.parseLong(request.getParameter(SECTION_ID));
            String currentPageParameter = request.getParameter(PAGINATION_PAGE);
            int currentPage = 1;

            if(currentPageParameter != null){
                currentPage = Integer.parseInt(currentPageParameter);
            }

            int totalRecords = catalogService.readRowCountBySection(sectionId);
            int offset = PaginationService.offset(PAGE_SIZE, currentPage);
            List<Postcard> catalogSublist = catalogService.findPostcardsSublistBySectionId(PAGE_SIZE, offset, sectionId);

            if(catalogSublist.isEmpty() && currentPage > 1){
                currentPage--;
                offset = PaginationService.offset(PAGE_SIZE, currentPage);
                catalogSublist = catalogService.findPostcardsSublistBySectionId(PAGE_SIZE, offset, sectionId);
            }

            int pages = PaginationService.pages(totalRecords, PAGE_SIZE);
            int lastPage = PaginationService.lastPage(pages, PAGE_SIZE, totalRecords);

            request.setAttribute(POSTCARD_LIST, catalogSublist);
            request.setAttribute(PAGINATION_PAGE, currentPage);
            request.setAttribute(PAGINATION_LAST_PAGE, lastPage);
            StringBuilder builderUrl = new StringBuilder(Command.createURL(request, request.getParameter(COMMAND)));
            builderUrl.append(SIGN).append(SECTION_ID).append(EQUAL).append(sectionId);
            request.setAttribute(URL, builderUrl.toString());
            request.setAttribute(COMMAND_URL, request.getContextPath() + "/controller?" + COMMAND + EQUAL + "find_all_postcard_by_section" + SIGN + "section_id" + EQUAL + sectionId);
            router.setCurrentPage(MENU_PAGE);
        } catch (ServiceException | NumberFormatException e) {
            throw new CommandException("Exception in a FindAllPostcardCommand class", e);
        }
        return router;
    }
}
