package me.kodingking.bots.kodax.listeners;

import me.kodingking.bots.kodax.handlers.CommandHandler;
import me.kodingking.bots.kodax.handlers.LevellingHandler;
import me.kodingking.bots.kodax.util.Multithreading;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class JDAGuildListener extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }

        Multithreading.runAsync(() -> {
            CommandHandler.processCommand(event.getMessage());
            LevellingHandler.handleMessage(event.getMessage());
        });
    }
}
