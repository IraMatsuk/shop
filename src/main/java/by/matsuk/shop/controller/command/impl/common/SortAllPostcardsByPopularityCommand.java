package by.matsuk.shop.controller.command.impl.common;

import by.matsuk.shop.controller.Router;
import by.matsuk.shop.controller.command.Command;
import by.matsuk.shop.entity.Postcard;
import by.matsuk.shop.exception.CommandException;
import by.matsuk.shop.exception.ServiceException;
import by.matsuk.shop.model.service.MenuService;
import by.matsuk.shop.model.service.PaginationService;
import by.matsuk.shop.model.service.impl.MenuServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static by.matsuk.shop.controller.Parameter.*;
import static by.matsuk.shop.controller.PathPage.MENU_PAGE;

/**
 * The type Sort all postcards by popularity command.
 */
public class SortAllPostcardsByPopularityCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final int PAGE_SIZE = 4;
    private final MenuService menuService = MenuServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        String currentPageParameter = request.getParameter(PAGINATION_PAGE);
        String sectionId = request.getParameter(SECTION_ID);
        int currentPage = 1;
        if(currentPageParameter != null){
            currentPage = Integer.parseInt(currentPageParameter);
        }

        int offset = PaginationService.offset(PAGE_SIZE, currentPage);
        int totalRecords;
        try {
            List<Postcard> menuSublist;
            StringBuilder builderUrl;
            if(sectionId == null) {
                menuSublist = menuService.findSortedPostcardsSubListByPopularity(PAGE_SIZE, offset);
                logger.info(menuSublist);
                if(menuSublist.isEmpty() && currentPage > 1){
                    currentPage--;
                    offset = PaginationService.offset(PAGE_SIZE, currentPage);
                    menuSublist = menuService.findSortedPostcardsSubListByPopularity(PAGE_SIZE, offset);
                    logger.info(menuSublist);
                }
                totalRecords = menuService.readRowCount();
                builderUrl = new StringBuilder(Command.createURL(request, request.getParameter(COMMAND)));
            }else{
                long id = Long.parseLong(sectionId);
                logger.info("id = " + id);
                menuSublist = menuService.findSortedPostcardsSectionSubListByPopularity(PAGE_SIZE, offset, id);
                if(menuSublist.isEmpty() && currentPage > 1){
                    currentPage--;
                    offset = PaginationService.offset(PAGE_SIZE, currentPage);
                    menuSublist = menuService.findSortedPostcardsSectionSubListByPopularity(PAGE_SIZE, offset, id);
                }
                totalRecords = menuService.readRowCountBySection(id);
                builderUrl = new StringBuilder(Command.createURL(request, request.getParameter(COMMAND)));
                builderUrl.append(SIGN).append(SECTION_ID).append(EQUAL).append(sectionId);
            }

            int pages = PaginationService.pages(totalRecords, PAGE_SIZE);
            int lastPage = PaginationService.lastPage(pages, PAGE_SIZE, totalRecords);
            request.setAttribute(POSTCARD_LIST, menuSublist);
            request.setAttribute(PAGINATION_PAGE, currentPage);
            request.setAttribute(PAGINATION_LAST_PAGE, lastPage);
            request.setAttribute(URL, builderUrl.toString());
            router.setCurrentPage(MENU_PAGE);
        } catch (ServiceException | NumberFormatException e) {
            throw new CommandException("Exception in a SortAllPostcardsByPriceCommand class. ", e);
        }
        return router;
    }
}
