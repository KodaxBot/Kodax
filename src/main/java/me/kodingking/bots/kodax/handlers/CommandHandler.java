package me.kodingking.bots.kodax.handlers;

import com.github.fcannizzaro.material.Colors;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import me.kodingking.bots.kodax.Kodax;
import me.kodingking.bots.kodax.command.AbstractCommand;
import me.kodingking.bots.kodax.command.Command;
import me.kodingking.bots.kodax.command.permission.EnumPermission;
import me.kodingking.bots.kodax.util.CommandUtil;
import me.kodingking.bots.kodax.util.MemberUtil;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.reflections.Reflections;

public class CommandHandler {

    public static final String PREFIX = "-";

    private static final List<AbstractCommand> commandList = new ArrayList<>();

    public static void init() {
        new Reflections("me.kodingking.bots.kodax.command.impl")
            .getTypesAnnotatedWith(Command.class).forEach(aClass -> {
            LoggingHandler.LOGGER.debug("Loading command class: " + aClass.getName());
            try {
                commandList.add((AbstractCommand) aClass.newInstance());
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        });
    }

    public static void processCommand(Message message) {
        processCommand(message.getMember(), message.getChannel(), message.getContentRaw(), message,
            false, false);
    }

    public static void processCommand(Member author, MessageChannel channel, String content,
        Message message, boolean onlyShowHelp, boolean minimalHelp) {
        if (author.getUser().isBot() || author.getUser().isFake() || !content
            .startsWith(PREFIX)) {
            return;
        }

        String command = content.substring(PREFIX.length()).split(" ")[0];
        String argsTrimmed = content.substring(command.length() + PREFIX.length()).trim();
        String[] args = argsTrimmed.isEmpty() ? new String[0] : argsTrimmed.split(" ");

        Predicate<AbstractCommand> commandPredicate = abstractCommand -> abstractCommand.getName()
            .equalsIgnoreCase(command);

        if (commandList.stream().anyMatch(commandPredicate)) {
            AbstractCommand abstractCommand = commandList.stream().filter(commandPredicate)
                .findFirst().get();

            EnumPermission permission = MemberUtil.getPermissionLevel(author);
            if (abstractCommand.getPermission().ordinal() < permission.ordinal()) {
                channel.sendMessage(
                    new EmbedBuilder()
                        .setTitle(LocaleHandler.getString("handlers.command.permissionErrorTitle"))
                        .setDescription(String.format(
                            LocaleHandler.getString(Kodax.SETTINGS.isSassMode()
                                ? "handlers.command.permissionErrorDescriptionSass"
                                : "handlers.command.permissionErrorDescription"),
                            permission.getDisplayName()))
                        .setColor(Colors.red_400.asColor())
                        .build()
                ).queue();
                return;
            }

            CommandLineParser parser = new DefaultParser();
            HelpFormatter formatter = new HelpFormatter();
            CommandLine cmd;

            Options options = abstractCommand.getOptions();

            String usage = CommandUtil
                .getOptions(formatter, options);

            if (onlyShowHelp || (args.length > 0 && (args[0].equalsIgnoreCase("?") || args[0]
                .equalsIgnoreCase("help")))) {
                EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setTitle(String.format(LocaleHandler.getString("handlers.command.helpTitle"), abstractCommand.getName()))
                    .setColor(Colors.blue_400.asColor())
                    .addField(LocaleHandler.getString("words.name"), abstractCommand.getName(), true)
                    .addField(LocaleHandler.getString("words.description"), abstractCommand.getDescription(), true)
                    .addField(LocaleHandler.getString("words.usage"), abstractCommand.getUsage(), true);
                if (!minimalHelp) {
                    embedBuilder.addField(LocaleHandler.getString("words.examples"),
                        "```" + String.join("\n", abstractCommand.getExamples()) + "```", false)
                        .addField(LocaleHandler.getString("words.category"), abstractCommand.getHelpCategory().getName(), true)
                        .addField(LocaleHandler.getString("words.requiredPermissionLevel"),
                            abstractCommand.getPermission().getDisplayName(), true)
                        .addField(LocaleHandler.getString("words.options"), usage.trim().isEmpty() ? LocaleHandler.getString("handlers.command.noOptions") : usage, true);
                }
                channel.sendMessage(embedBuilder.build()).queue();
                return;
            }

            try {
                cmd = parser.parse(options, args);
            } catch (ParseException e) {
                channel.sendMessage(
                    new EmbedBuilder()
                        .setTitle(LocaleHandler.getString("handlers.command.commandError"))
                        .setColor(Colors.red_400.asColor())
                        .setDescription("**" + e.getMessage() + "**\n" + usage)
                        .build()
                ).queue();
                return;
            }

            abstractCommand.execute(message, cmd);
        }
    }

    public static List<AbstractCommand> getCommandList() {
        return commandList;
    }
}
