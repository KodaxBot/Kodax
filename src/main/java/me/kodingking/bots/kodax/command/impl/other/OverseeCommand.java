package me.kodingking.bots.kodax.command.impl.other;

import java.util.ArrayList;
import java.util.List;
import me.kodingking.bots.kodax.command.AbstractCommand;
import me.kodingking.bots.kodax.command.Command;
import me.kodingking.bots.kodax.command.help.EnumHelpCategory;
import me.kodingking.bots.kodax.command.permission.EnumPermission;
import me.kodingking.bots.kodax.handlers.LocaleHandler;
import net.dv8tion.jda.core.entities.Message;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

@Command(name = "oversee", usage = "oversee <say <message> [-d]>", category = EnumHelpCategory.NONE, permission = EnumPermission.BOT_OWNER, description = "commands.oversee.description", examples = {
    "commands.oversee.example1"
})
public class OverseeCommand extends AbstractCommand {

    @Override
    public void execute(Message message, CommandLine commandLine) {
        if (commandLine.getArgs().length == 0) {
            printUsage(message);
        } else {
            if (commandLine.hasOption("d")) {
                message.delete().queue();
            }
            if (commandLine.getArgs()[0].equalsIgnoreCase("say")) {
                List<String> sayMsg = new ArrayList<>(commandLine.getArgList());
                if (sayMsg.size() == 1) {
                    message.getChannel()
                        .sendMessage(LocaleHandler.getString("commands.oversee.sayMessageError"))
                        .queue();
                } else {
                    sayMsg.remove(0);
                    message.getChannel()
                        .sendMessage(String.join(" ", sayMsg.toArray(new String[0]))).queue();
                }
            } else {
                message.getChannel()
                    .sendMessage(LocaleHandler.getString("commands.general.invalidSub")).queue();
            }
        }
    }

    @Override
    public Options getOptions() {
        Options options = new Options();

        Option deleteOption = new Option("d", "delete", false,
            LocaleHandler.getString("commands.oversee.optionDelete"));
        deleteOption.setRequired(false);
        options.addOption(deleteOption);

        return options;
    }
}
