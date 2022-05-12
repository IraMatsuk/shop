package by.matsuk.shop.controller;

import by.matsuk.shop.controller.command.*;
import by.matsuk.shop.controller.factory.CommandType;
import by.matsuk.shop.exception.CommandException;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Optional;

import static by.matsuk.shop.controller.Parameter.COMMAND;
import static by.matsuk.shop.controller.PathPage.ERROR_500;

/**
 * @author Ira
 * The type Controller.
 * @project shop
 */
@WebServlet(urlPatterns = {"/controller"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 25)
public class Controller extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String commandName = request.getParameter(COMMAND);
        LOGGER.debug("The command is " + commandName);
        Optional<Command> command = CommandType.provideCommand(commandName);
        try {
            Router router;
            if (command.isPresent()) {
                router = command.get().execute(request);
                String page = router.getCurrentPage();
                if (router.getCurrentType() == Router.Type.FORWARD) {
                    LOGGER.info("Forward type.");
                    request.getRequestDispatcher(page).forward(request, response);
                } else {
                    LOGGER.info("Redirect type. Page: " + page);
                    response.sendRedirect(page);
                }
            } else {
                response.sendRedirect(ERROR_500);
            }
        } catch (CommandException e) {
            LOGGER.error(e);
            response.sendRedirect(ERROR_500);
        }
    }
}
