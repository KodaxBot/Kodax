package me.kodingking.bots.kodax;

import me.kodingking.bots.kodax.core.KodaxSettings;
import me.kodingking.bots.kodax.database.KodaxDatabase;
import me.kodingking.bots.kodax.handlers.CommandHandler;
import me.kodingking.bots.kodax.handlers.LocaleHandler;
import me.kodingking.bots.kodax.handlers.LoggingHandler;
import me.kodingking.bots.kodax.listeners.JDACoreListener;
import me.kodingking.bots.kodax.listeners.JDAGuildListener;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Game.GameType;

public class Kodax {

    public static Kodax INSTANCE;
    public static KodaxSettings SETTINGS;

    public static JDA BOT;

    public void start() throws Exception {
        LoggingHandler.setupLogging();
        LoggingHandler.LOGGER.info("Starting Kodax (Debug: " + SETTINGS.isDebug() + ")");

        if (SETTINGS.isSassMode()) {
            LoggingHandler.LOGGER.info("Hey look, I'm in sass mode >:)");
        }

        LoggingHandler.LOGGER.info("Performing startup sequence");

        LoggingHandler.LOGGER.info("Loading handlers");
        KodaxDatabase.init();
        CommandHandler.init();
        LocaleHandler.init();

        LoggingHandler.LOGGER.debug("Loading JDA");
        BOT = new JDABuilder(AccountType.BOT)
            .setAutoReconnect(true)
            .setToken(SETTINGS.getToken())
            .addEventListener(
                new JDACoreListener(),
                new JDAGuildListener()
            )
            .build();

        LoggingHandler.LOGGER.debug("Loading previous settings");
        String game = KodaxDatabase.getSetting("Game", "");
        if (!game.isEmpty()) {
            BOT.getPresence().setPresence(Game.of(GameType.DEFAULT, game), false);
        }

        LoggingHandler.LOGGER.info("Done! Waiting for JDA to finish");
    }

}
