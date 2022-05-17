package by.matsuk.shop.controller.command.impl.common;

import by.matsuk.shop.controller.Router;
import by.matsuk.shop.controller.command.Command;
import by.matsuk.shop.entity.Postcard;
import by.matsuk.shop.entity.Section;
import by.matsuk.shop.exception.CommandException;
import by.matsuk.shop.exception.ServiceException;
import by.matsuk.shop.model.service.MenuService;
import by.matsuk.shop.model.service.PaginationService;
import by.matsuk.shop.model.service.impl.MenuServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;

import static by.matsuk.shop.controller.Parameter.*;
import static by.matsuk.shop.controller.PathPage.MENU_PAGE;
import static by.matsuk.shop.controller.SessionAttribute.SECTION_LIST;

/**
 * The type Find all postcard command.
 */
public class FindAllPostcardCommand implements Command {
    private static final int PAGE_SIZE = 4;
    private final MenuService menuService = MenuServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        try {
            String currentPageParameter = request.getParameter(PAGINATION_PAGE);
            int currentPage = 1;
            if (currentPageParameter != null) {
                currentPage = Integer.parseInt(currentPageParameter);
            }

            int offset = PaginationService.offset(PAGE_SIZE, currentPage);
            List<Postcard> postcardSublist = menuService.findPostcardsSublist(PAGE_SIZE, offset);
            if (postcardSublist.isEmpty() && currentPage > 1) {
                currentPage--;
                offset = PaginationService.offset(PAGE_SIZE, currentPage);
                postcardSublist = menuService.findPostcardsSublist(PAGE_SIZE, offset);
            }
            int totalRecords = menuService.readRowCount();
            int pages = PaginationService.pages(totalRecords, PAGE_SIZE);
            int lastPage = PaginationService.lastPage(pages, PAGE_SIZE, totalRecords);

            request.setAttribute(POSTCARD_LIST, postcardSublist);
            request.setAttribute(PAGINATION_PAGE, currentPage);
            request.setAttribute(PAGINATION_LAST_PAGE, lastPage);
            request.setAttribute(URL, MENU_PAGE);
            request.setAttribute(COMMAND_URL, request.getContextPath() + "/controller?" + COMMAND + EQUAL + "find_all_postcard");
            router.setCurrentPage(MENU_PAGE);
        } catch (ServiceException | NumberFormatException e) {
            throw new CommandException("Exception in a FindAllPostcardCommand class", e);
        }
        return router;
    }
}

