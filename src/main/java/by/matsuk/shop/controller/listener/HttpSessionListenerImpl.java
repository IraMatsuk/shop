package by.matsuk.shop.controller.listener;

import by.matsuk.shop.controller.command.PagePath;
import by.matsuk.shop.entity.UserRole;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

import static by.matsuk.shop.controller.command.SessionAttribute.*;

/**
 * @project Postcard shop
 * @author Ira
 * The type Http session listener.
 */
@WebListener
public class HttpSessionListenerImpl implements HttpSessionListener {
    private static final String DEFAULT_LOCALE = "en_US";
    private static final String DEFAULT_LANGUAGE = "EN";

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        HttpSession httpSession = httpSessionEvent.getSession();
        httpSession.setAttribute(CURRENT_PAGE, PagePath.HOME);
        httpSession.setAttribute(LOCALE, DEFAULT_LOCALE);
        httpSession.setAttribute(LANGUAGE, DEFAULT_LANGUAGE);
        httpSession.setAttribute(ROLE, UserRole.GUEST.getRole());
    }
}
