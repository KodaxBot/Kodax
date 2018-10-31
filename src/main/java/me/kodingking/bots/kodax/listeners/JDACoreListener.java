package me.kodingking.bots.kodax.listeners;

import me.kodingking.bots.kodax.handlers.LoggingHandler;
import net.dv8tion.jda.core.events.ExceptionEvent;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class JDACoreListener extends ListenerAdapter {

    @Override
    public void onReady(ReadyEvent event) {
        LoggingHandler.LOGGER.info("JDA has started");
    }

    @Override
    public void onException(ExceptionEvent event) {
        LoggingHandler.error(event.getCause());
    }
}
