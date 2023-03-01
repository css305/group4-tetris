package resources;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Logging utility for the G4Tetris Application.
 * @author Zac Andersen (anderzb@uw.edu)
 * @version 0.1
 */
public final class G4Logging {

    /** File handler for all loggers. */
    private static FileHandler FILE_HANDLER;

    /** Console handler for all loggers. */
    private static final ConsoleHandler CONSOLE_HANDLER = new ConsoleHandler();

    /**
     * Utility Class - Do Not Instantiate.
     */
    private G4Logging() {
        //no.
    }

    /**
     * Initializes the logging system.
     */
    public static void initLogging() {
        try {
            FILE_HANDLER = new FileHandler("g4tetris.log", true);
        } catch (final IOException e) {
            System.err.println("Error: Failed initialize logging file handler.");
        }

        final String logLevel = System.getenv("LOG_LEVEL");
        final Level level = logLevel != null ? Level.parse(logLevel) : Level.FINE;

        FILE_HANDLER.setLevel(level);
        CONSOLE_HANDLER.setLevel(level);

    }

    /**
     * Gets a logger for the given class.
     * @param theClass Class to log from.
     * @return Logger for class.
     */
    public static Logger getLogger(final Class theClass) {

        final Logger logger = Logger.getLogger(theClass.getName());

        final String consoleENV = System.getenv("CONSOLE_OUTPUT");
        final boolean consoleOutput = Boolean.parseBoolean(consoleENV);

        logger.setUseParentHandlers(false);

        logger.addHandler(FILE_HANDLER);
        if (consoleOutput) {
            logger.addHandler(CONSOLE_HANDLER);
        }

        return logger;

    }
}
