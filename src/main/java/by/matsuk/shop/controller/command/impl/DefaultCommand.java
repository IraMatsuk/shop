package by.matsuk.shop.controller.command.impl;

import by.matsuk.shop.controller.command.Command;
import by.matsuk.shop.controller.command.PagePath;
import by.matsuk.shop.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @project Postcard shop
 * @author Ira
 * The type Default command.
 */
public class DefaultCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        return new Router(PagePath.ERROR_404, Router.RouterType.REDIRECT);
    }
}
