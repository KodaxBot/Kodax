package me.kodingking.bots.kodax.listeners;

import me.kodingking.bots.kodax.handlers.CommandHandler;
import me.kodingking.bots.kodax.util.Multithreading;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class JDAGuildListener extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        Multithreading.runAsync(() -> CommandHandler.processCommand(event.getMessage()));
    }
}
