package me.kodingking.bots.kodax.command.impl.utilities;

import com.github.fcannizzaro.material.Colors;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import me.kodingking.bots.kodax.command.AbstractCommand;
import me.kodingking.bots.kodax.command.Command;
import me.kodingking.bots.kodax.command.help.EnumHelpCategory;
import me.kodingking.bots.kodax.handlers.CommandHandler;
import me.kodingking.bots.kodax.handlers.LocaleHandler;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

@Command(name = "help", usage = "help [category|command]", category = EnumHelpCategory.UTILITIES, description = "commands.help.description", examples = {
    "commands.help.example1",
    "commands.help.example2"
})
public class HelpCommand extends AbstractCommand {

    @Override
    public void execute(Message message, CommandLine commandLine) {
        if (commandLine.getArgs().length == 0) {
            EmbedBuilder helpMessage = new EmbedBuilder()
                .setTitle(LocaleHandler.getString("commands.help.categoryTitle"))
                .setColor(Colors.green_400.asColor());
            IntStream.range(0, EnumHelpCategory.getItems().length).forEach(i -> {
                EnumHelpCategory category = EnumHelpCategory.getItems()[i];
                helpMessage
                    .addField(String.format("%s %s", category.getEmoji(), category.getName()), String.format(LocaleHandler.getString("commands.help.categoryDescription"), category.getDescription(), CommandHandler.getCommandList().stream().filter(abstractCommand -> abstractCommand.getHelpCategory() == category).count()),
                        true);
            });
            message.getChannel().sendMessage(helpMessage.build()).queue();
        } else {
            String name = commandLine.getArgs()[0];

            Predicate<AbstractCommand> commandPredicate = abstractCommand -> abstractCommand.getName().equalsIgnoreCase(name);
            Predicate<EnumHelpCategory> helpCategoryPredicate = enumHelpCategory -> enumHelpCategory.getName().equalsIgnoreCase(name);

            if (CommandHandler.getCommandList().stream().anyMatch(commandPredicate)) {
                AbstractCommand command = CommandHandler.getCommandList().stream().filter(commandPredicate).findFirst().get();
                CommandHandler.processCommand(message.getMember(), message.getChannel(), CommandHandler.PREFIX + command.getName() + " ?", message, true, false);
            } else if (Arrays.stream(EnumHelpCategory.getItems()).anyMatch(helpCategoryPredicate)) {
                EnumHelpCategory helpCategory = Arrays.stream(EnumHelpCategory.getItems()).filter(helpCategoryPredicate).findFirst().get();
                EmbedBuilder helpMessage = new EmbedBuilder()
                    .setTitle(String.format(LocaleHandler.getString("commands.help.categoryInfoTitle"), helpCategory.getEmoji(), helpCategory.getName()))
                    .setDescription(helpCategory.getDescription())
                    .setColor(Colors.green_400.asColor());
                if (CommandHandler.getCommandList().stream().noneMatch(
                    abstractCommand -> abstractCommand.getHelpCategory() == helpCategory)) {
                    helpMessage
                            .setTitle(String.format(LocaleHandler.getString("commands.help.categoryInfoTitleError"), helpCategory.getName()))
                            .setDescription(String.format(LocaleHandler.getString("commands.help.categoryInfoDescriptionError"), helpCategory.getDescription()))
                            .setColor(Colors.red_400.asColor());
                } else {
                    CommandHandler.getCommandList().stream().filter(abstractCommand -> abstractCommand.getHelpCategory() == helpCategory).forEach(abstractCommand -> helpMessage
                        .addField(abstractCommand.getName(), abstractCommand.getDescription() + "\n*" + abstractCommand.getUsage() + "*", true));
                }
                message.getChannel().sendMessage(helpMessage.build()).queue();
            } else {
                message.getChannel().sendMessage(LocaleHandler.getString("commands.help.noResults")).queue();
            }
        }
    }

    @Override
    public Options getOptions() {
        return new Options();
    }
}
