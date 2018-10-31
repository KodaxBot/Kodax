package me.kodingking.bots.kodax.handlers;

import me.kodingking.bots.kodax.Kodax;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

public class LoggingHandler {

    public static final Logger LOGGER = LogManager.getLogger("Kodax");

    public static void setupLogging() {
        if (Kodax.SETTINGS.isDebug()) {
            Configurator.setRootLevel(Level.DEBUG);
        } else {
            Configurator.setRootLevel(Level.INFO);
        }
    }

    public static void error(Object object) {
        LOGGER.error(object);
    }

}
