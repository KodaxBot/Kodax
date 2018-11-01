package me.kodingking.bots.kodax.handlers;

import java.util.HashMap;
import java.util.Map;
import me.kodingking.bots.kodax.database.KodaxDatabase;
import net.dv8tion.jda.core.entities.Message;

public class LevellingHandler {

    private static Map<String, Long> coolDownUsers = new HashMap<>();

    public static void handleMessage(Message message) {
        if (coolDownUsers.containsKey(message.getAuthor().getId()) && coolDownUsers.get(message.getAuthor().getId()) + 10 * 1000 < System.currentTimeMillis()) {
            coolDownUsers.remove(message.getAuthor().getId());
        }
        if (coolDownUsers.containsKey(message.getAuthor().getId())) {
            return;
        }
        coolDownUsers.put(message.getAuthor().getId(), System.currentTimeMillis());

        double dbCurrentXp = Double.valueOf(
            (String) KodaxDatabase.getUserValue(message.getAuthor().getId(), "xp", 0));
        double dbCurrentLevel = Double.valueOf(
            (String) KodaxDatabase.getUserValue(message.getAuthor().getId(), "level", 0));

        double currentLevel = Math.floor(calcCurrentLevel(dbCurrentXp));

        if (dbCurrentLevel < currentLevel) {
            message.getChannel().sendMessage(String.format(LocaleHandler.getString("handlers.levelling.levelUp"), (int) currentLevel)).queue();
            KodaxDatabase.setUserValue(message.getAuthor().getId(), "level", currentLevel);
        }

        KodaxDatabase.setUserValue(message.getAuthor().getId(), "xp", dbCurrentXp + 1);
    }

    public static double calcCurrentLevel(double currentXp) {
        return 0.46d * Math.sqrt(currentXp + 1);
    }

}
