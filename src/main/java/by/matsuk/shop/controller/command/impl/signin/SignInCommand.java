package by.matsuk.shop.controller.command.impl.signin;

import by.matsuk.shop.controller.command.Command;
import by.matsuk.shop.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @project Postcard shop
 * @author Ira
 * The type Sign in command.
 */
public class SignInCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String SIGN_IN_ERROR_MESSAGE_KEY = "error.sign_in";

    @Override
    public Router execute(HttpServletRequest request) {
        return null;
    }
}
