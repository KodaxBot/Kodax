package me.kodingking.bots.kodax.command.impl.searching;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

import com.github.fcannizzaro.material.Colors;

import me.kodingking.bots.kodax.command.AbstractCommand;
import me.kodingking.bots.kodax.command.Command;
import me.kodingking.bots.kodax.command.help.EnumHelpCategory;
import me.kodingking.bots.kodax.handlers.LocaleHandler;
import me.kodingking.bots.kodax.wrappers.UrbanDictionary;
import me.kodingking.bots.kodax.wrappers.UrbanDictionary.RequestResult;

@Command(name = "urban", usage = "urban <word>", description = "commands.urban.description", category = EnumHelpCategory.SEARCHING, examples = {
    "commands.urban.example1"
})
public class UrbanDictionaryCommand extends AbstractCommand {

    @Override
    public void execute(Message message, CommandLine commandLine) {
        if (commandLine.getArgs().length == 0) {
            message.getChannel().sendMessage(LocaleHandler.getString("commands.urban.notEnoughArgs")).queue();
        } else {
            try {
                RequestResult result = UrbanDictionary.define(String.join("%20", commandLine.getArgs()));
                if (result.isErrored()) {
                    message.getChannel().sendMessage(LocaleHandler.getString("commands.urban.error")).queue();
                } else {
                    message.getChannel().sendMessage(new EmbedBuilder()
                            .setTitle(String.format(LocaleHandler.getString("commands.urban.resultTitle"), result.getWord()))
                            .setColor(Colors.blue_400.asColor())
                            .setDescription(String.format(LocaleHandler.getString("commands.urban.resultDescription"), 
                                result.getWord(), 
                                result.getAuthor(),
                                result.getDefinition(),
                                result.getExample(),
                                result.getPermalink()
                            ))
                            .build()
                    ).queue();
                }
            } catch (Exception e) {
                message.getChannel().sendMessage(LocaleHandler.getString("commands.urban.exception")).queue();
            }
        }
    }

    @Override
    public Options getOptions() {
        return new Options();
    }
    
}
