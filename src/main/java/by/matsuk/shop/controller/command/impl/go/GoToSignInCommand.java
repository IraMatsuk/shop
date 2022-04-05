package by.matsuk.shop.controller.command.impl.go;

import by.matsuk.shop.controller.command.Command;
import by.matsuk.shop.controller.command.PagePath;
import by.matsuk.shop.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @project Postcard shop
 * @author Ira
 * The type Go to sign in command.
 */
public class GoToSignInCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        return new Router(PagePath.SIGN_IN, Router.RouterType.FORWARD);
    }
}
