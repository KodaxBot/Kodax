package me.kodingking.bots.kodax.command.impl.utilities;

import java.text.DecimalFormat;
import me.kodingking.bots.kodax.command.AbstractCommand;
import me.kodingking.bots.kodax.command.Command;
import me.kodingking.bots.kodax.command.help.EnumHelpCategory;
import me.kodingking.bots.kodax.database.KodaxDatabase;
import me.kodingking.bots.kodax.handlers.LevellingHandler;
import me.kodingking.bots.kodax.handlers.LocaleHandler;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

@Command(name = "level", usage = "level", category = EnumHelpCategory.UTILITIES, description = "commands.level.description", examples = {
    "commands.level.example1"
})
public class LevelCommand extends AbstractCommand {

    @Override
    public void execute(Message message, CommandLine commandLine) {
        double dbCurrentXp = Double.valueOf(
            (String) KodaxDatabase.getUserValue(message.getAuthor().getId(), "xp", 0));
        double currentLevel = LevellingHandler.calcCurrentLevel(dbCurrentXp);

        message.getChannel().sendMessage(String.format(LocaleHandler.getString("commands.level.message"),
            (int) Math.floor(currentLevel), new DecimalFormat("#.##").format(Math.abs((currentLevel - Math.floor(currentLevel)) * 100)))).queue();
    }

    @Override
    public Options getOptions() {
        return new Options();
    }
}
