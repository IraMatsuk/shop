package by.matsuk.shop.controller.filter;

import by.matsuk.shop.controller.factory.CommandType;
import by.matsuk.shop.controller.filter.permission.UserPermission;
import by.matsuk.shop.entity.User;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;

import static by.matsuk.shop.controller.Parameter.COMMAND;
import static by.matsuk.shop.controller.PathPage.ERROR_404;
import static by.matsuk.shop.controller.SessionAttribute.USER;

/**
 * The type Page security command filter.
 */
public class PageSecurityCommandFilter implements Filter {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        logger.info("PageSecurityFilter - doFilter");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpSession session = httpServletRequest.getSession();
        String command = httpServletRequest.getParameter(COMMAND);
        if (command == null) {
            request.getRequestDispatcher(ERROR_404).forward(httpServletRequest, httpServletResponse);
            return;
        }
        User.UserRole role = User.UserRole.GUEST;
        Set<String> commands;

        User user = (User) session.getAttribute(USER);
        if (user != null) {
            role = user.getRole();
        }

        switch (role) {
            case ADMIN -> commands = UserPermission.ADMIN.getCommands();
            case CLIENT -> commands = UserPermission.CLIENT.getCommands();
            default -> commands = UserPermission.GUEST.getCommands();
        }

        boolean isCorrect = Arrays.stream(CommandType.values())
                .anyMatch(commandType -> command.equalsIgnoreCase(commandType.toString()));

        if (isCorrect && !commands.contains(command.toUpperCase())) {
            httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        if (!commands.contains(command.toUpperCase())) {
            logger.info("command = " + command);
            request.getRequestDispatcher(ERROR_404)
                    .forward(httpServletRequest, httpServletResponse);
            return;
        }
        logger.info("Chain continue");
        chain.doFilter(request, response);
    }
}
