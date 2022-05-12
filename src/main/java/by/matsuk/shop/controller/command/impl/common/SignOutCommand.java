package by.matsuk.shop.controller.command.impl.common;

import by.matsuk.shop.controller.Router;
import by.matsuk.shop.controller.command.Command;
import by.matsuk.shop.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static by.matsuk.shop.controller.PathPage.HOME_PAGE;
import static by.matsuk.shop.controller.SessionAttribute.LANGUAGE;

/**
 * The type Sign out command.
 * @author Ira
 * @project Postcard shop
 */
public class SignOutCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        String language = (String) session.getAttribute(LANGUAGE);
        session.invalidate();
        session = request.getSession(true);
        session.setAttribute(LANGUAGE, language);
        router.setRedirectType();
        router.setCurrentPage(request.getContextPath() + HOME_PAGE);
        return router;
    }
}
