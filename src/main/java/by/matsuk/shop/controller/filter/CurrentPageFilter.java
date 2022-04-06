package by.matsuk.shop.controller.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static by.matsuk.shop.controller.Parameter.COMMAND;
import static by.matsuk.shop.controller.SessionAttribute.CURRENT_PAGE;

/**
 * @project Postcard shop "Card4You"
 * @author Ira
 * The type Current page filter.
 */
public class CurrentPageFilter implements Filter {
    private static final Logger logger = LogManager.getLogger();
    private static final String CONTROLLER = "/controller?";
    private static final String QUESTION = "?";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpSession session = servletRequest.getSession();
        String requestURI = servletRequest.getRequestURI();
        logger.info("request URI: " + requestURI);
        String query = servletRequest.getQueryString();
        if(query != null){
            if(servletRequest.getParameter(COMMAND) != null) {
                requestURI = CONTROLLER + query;
            } else {
                requestURI = servletRequest.getContextPath() + servletRequest.getServletPath() + QUESTION + query;
            }
        }
        logger.info(query);
        session.setAttribute(CURRENT_PAGE, requestURI);
        chain.doFilter(request, response);
    }
}
