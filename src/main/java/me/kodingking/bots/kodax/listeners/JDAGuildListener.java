package me.kodingking.bots.kodax.listeners;

import me.kodingking.bots.kodax.handlers.CommandHandler;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class JDAGuildListener extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        CommandHandler.processCommand(event.getMessage());
    }
}
