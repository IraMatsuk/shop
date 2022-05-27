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
 * The type Sort all postcards by price command.
 */
public class SortAllPostcardsByPriceCommand implements Command {
    private static final int PAGE_SIZE = 4;
    private final CatalogService catalogService = CatalogServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        String sectionId = request.getParameter(SECTION_ID);
        int currentPage = currentPaginationPage(request);
        int offset = PaginationService.offset(PAGE_SIZE, currentPage);
        int totalRecords;
        try {
            List<Postcard> postcardSublist;
            StringBuilder builderUrl;
            if (sectionId == null) {
                postcardSublist = catalogService.sortAllPostcardsByPrice(PAGE_SIZE, offset);
                if (postcardSublist.isEmpty() && currentPage > 1) {
                    currentPage--;
                    offset = PaginationService.offset(PAGE_SIZE, currentPage);
                    postcardSublist = catalogService.sortAllPostcardsByPrice(PAGE_SIZE, offset);
                }
                totalRecords = catalogService.readRowCount();
                builderUrl = new StringBuilder(Command.createURL(request, request.getParameter(COMMAND)));
            } else {
                long id = Long.parseLong(sectionId);
                postcardSublist = catalogService.sortSectionPostcardsByPrice(PAGE_SIZE, offset, id);
                if (postcardSublist.isEmpty() && currentPage > 1) {
                    currentPage--;
                    offset = PaginationService.offset(PAGE_SIZE, currentPage);
                    postcardSublist = catalogService.sortSectionPostcardsByPrice(PAGE_SIZE, offset, id);
                }
                totalRecords = catalogService.readRowCountBySection(id);
                builderUrl = new StringBuilder(Command.createURL(request, request.getParameter(COMMAND)));
                builderUrl.append(SIGN).append(SECTION_ID).append(EQUAL).append(sectionId);
            }
            int pages = PaginationService.pages(totalRecords, PAGE_SIZE);
            int lastPage = PaginationService.lastPage(pages, PAGE_SIZE, totalRecords);
            addRequestAttribute(request, postcardSublist, currentPage, lastPage, builderUrl.toString());
            router.setCurrentPage(MENU_PAGE);
        } catch (ServiceException | NumberFormatException e) {
            throw new CommandException("Exception in a SortAllPostcardsByPriceCommand class. ", e);
        }
        return router;
    }

    private int currentPaginationPage(HttpServletRequest request) {
        String currentPageParameter = request.getParameter(PAGINATION_PAGE);
        int currentPage = 1;
        if (currentPageParameter != null) {
            currentPage = Integer.parseInt(currentPageParameter);
        }
        return currentPage;
    }

    private void addRequestAttribute(HttpServletRequest request, List<Postcard> postcardSublist, int currentPage, int lastPage, String url) {
        request.setAttribute(POSTCARD_LIST, postcardSublist);
        request.setAttribute(PAGINATION_PAGE, currentPage);
        request.setAttribute(PAGINATION_LAST_PAGE, lastPage);
        request.setAttribute(URL, url);
        request.setAttribute(COMMAND_URL, request.getContextPath() + "/controller?" + COMMAND + EQUAL + "sort_all_postcards_by_price");
    }
}
