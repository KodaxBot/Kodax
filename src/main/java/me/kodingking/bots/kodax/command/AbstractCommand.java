package me.kodingking.bots.kodax.command;

import java.util.Arrays;
import me.kodingking.bots.kodax.command.help.EnumHelpCategory;
import me.kodingking.bots.kodax.command.permission.EnumPermission;
import me.kodingking.bots.kodax.handlers.CommandHandler;
import me.kodingking.bots.kodax.handlers.LocaleHandler;
import net.dv8tion.jda.core.entities.Message;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

public abstract class AbstractCommand {

    public abstract void execute(Message message, CommandLine commandLine);

    public abstract Options getOptions();

    public String getName() {
        return getClass().getDeclaredAnnotation(Command.class).name();
    }

    public String getUsage() {
        return getClass().getDeclaredAnnotation(Command.class).usage();
    }

    public String getDescription() {
        String description = getClass().getDeclaredAnnotation(Command.class).description();
        return LocaleHandler.getString(description);
    }

    public String[] getExamples() {
        return Arrays.stream(getClass().getDeclaredAnnotation(Command.class).examples()).map(
            LocaleHandler::getString).toArray(String[]::new);
    }

    public EnumPermission getPermission() {
        return getClass().getDeclaredAnnotation(Command.class).permission();
    }

    public EnumHelpCategory getHelpCategory() {
        return getClass().getDeclaredAnnotation(Command.class).category();
    }

    protected void printUsage(Message message) {
        CommandHandler.processCommand(message.getMember(), message.getChannel(), message.getContentRaw(), message, true, true);
    }
}
