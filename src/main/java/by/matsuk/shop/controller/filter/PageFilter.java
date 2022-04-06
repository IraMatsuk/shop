package by.matsuk.shop.controller.filter;

import by.matsuk.shop.controller.filter.permission.PagePermission;
import by.matsuk.shop.entity.User;
import jakarta.servlet.*;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Set;

import static by.matsuk.shop.controller.PathPage.START_PAGE;
import static by.matsuk.shop.controller.SessionAttribute.USER;

/**
 * @project Postcard shop "Card4You"
 * @author Ira
 * The type Page filter.
 */
public class PageFilter implements Filter {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession();
        String requestURI = httpRequest.getServletPath();
        logger.info("Page URI: " + requestURI);

        User.UserRole userRole = User.UserRole.GUEST;
        User user = (User) session.getAttribute(USER);
        if(user != null){
            userRole = user.getRole();
            logger.info(userRole.toString());
        }
        logger.info(userRole);
        boolean isCorrect;
        Set<String> pages;
        switch (userRole){
            case ADMIN -> {
                pages = PagePermission.ADMIN.getUserPages();
                logger.info(pages);
                isCorrect = pages.stream().anyMatch(requestURI::contains);
            }
            case CLIENT -> {
                pages = PagePermission.CLIENT.getUserPages();
                logger.info(pages);
                isCorrect = pages.stream().anyMatch(requestURI::contains);
            }
            default -> {
                pages = PagePermission.GUEST.getUserPages();
                isCorrect = pages.stream().anyMatch(requestURI::contains);
            }
        }
        if(!isCorrect && user == null){
            user = new User();
            user.setRole(User.UserRole.GUEST);
            session.setAttribute(USER, user);
            httpResponse.sendRedirect(START_PAGE);
            return;
        }else if(!isCorrect){
            httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        chain.doFilter(request,response);
    }
}
