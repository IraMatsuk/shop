package by.matsuk.shop.controller.command;

import by.matsuk.shop.controller.command.impl.DefaultCommand;
import by.matsuk.shop.controller.command.impl.go.GoToHomeCommand;
import by.matsuk.shop.controller.command.impl.go.GoToSignInCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @project Postcard shop
 * @author Ira
 * The enum Command type.
 */
public enum CommandType {
    /**
     * The Go to home.
     */
    GO_TO_HOME(new GoToHomeCommand()),
    /**
     * The Go to sign in.
     */
    GO_TO_SIGN_IN(new GoToSignInCommand()),
    /**
     * The Default.
     */
    DEFAULT(new DefaultCommand());

    private final Command command;
    private static final Logger LOGGER = LogManager.getLogger();

    CommandType(Command command) {
        this.command = command;
    }

    /**
     * Gets command.
     *
     * @return the command
     */
    public Command getCommand() {
        return command;
    }

    /**
     * Define command command.
     *
     * @param commandType the command type
     * @return the command
     */
    public static Command defineCommand(String commandType) {
        if (commandType == null || commandType.isEmpty()) {
            return CommandType.DEFAULT.getCommand();
        }
        try {
            return CommandType.valueOf(commandType.toUpperCase()).getCommand();
        } catch (IllegalArgumentException e) {
            LOGGER.error("Error while defining command" + e);
            return CommandType.DEFAULT.getCommand();
        }
    }
}
