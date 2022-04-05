package by.matsuk.shop.controller.command.impl.go;

import by.matsuk.shop.controller.command.Command;
import by.matsuk.shop.controller.command.PagePath;
import by.matsuk.shop.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @project Postcard shop
 * @author Ira
 * The type Go to home command.
 */
public class GoToHomeCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        return new Router(PagePath.HOME, Router.RouterType.FORWARD);
    }
}
