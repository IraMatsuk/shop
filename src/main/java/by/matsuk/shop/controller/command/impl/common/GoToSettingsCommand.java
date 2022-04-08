package by.matsuk.shop.controller.command.impl.common;

import by.matsuk.shop.controller.Router;
import by.matsuk.shop.controller.command.Command;
import by.matsuk.shop.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

import static by.matsuk.shop.controller.PathPage.SETTINGS_PAGE;

/**
 * The type Go to settings command.
 */
public class GoToSettingsCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        router.setCurrentPage(SETTINGS_PAGE);
        return router;
    }
}