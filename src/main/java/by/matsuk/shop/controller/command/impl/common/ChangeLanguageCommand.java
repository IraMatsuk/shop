package by.matsuk.shop.controller.command.impl.common;

import by.matsuk.shop.controller.Router;
import by.matsuk.shop.controller.command.Command;
import by.matsuk.shop.util.LanguageUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.matsuk.shop.controller.SessionAttribute.CURRENT_PAGE;
import static by.matsuk.shop.controller.SessionAttribute.LANGUAGE;


/**
 * The type Change language command.
 */
public class ChangeLanguageCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        HttpSession session = request.getSession();
        String currentPage = (String) session.getAttribute(CURRENT_PAGE);
        String language = request.getParameter(LANGUAGE);
        if(!LanguageUtil.isCorrectLanguage(language)){
            router.setCurrentPage(currentPage);
            return router;
        }
        logger.info("Language parameter is " + language);
        logger.info("Current page is " + currentPage);
        session.setAttribute(LANGUAGE, language);
        router.setRedirectType();
        router.setCurrentPage(currentPage);
        return router;
    }
}
