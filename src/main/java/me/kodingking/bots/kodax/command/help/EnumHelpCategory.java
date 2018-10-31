package me.kodingking.bots.kodax.command.help;

import java.util.Arrays;
import me.kodingking.bots.kodax.handlers.LocaleHandler;

public enum EnumHelpCategory {

    MODERATION(":hammer:", "helpCategory.moderation.title", "helpCategory.moderation.description"),
    SEARCHING(":mag_right:", "helpCategory.searching.title", "helpCategory.searching.description"),
    UTILITIES(":notepad_spiral:", "helpCategory.utilities.title", "helpCategory.utilities.description"),
    MANAGEMENT(":gear:", "helpCategory.management.title", "helpCategory.management.description"),
    MUSIC(":musical_note:", "helpCategory.music.title", "helpCategory.music.description"),
    NONE(":x:", "None", "");

    private String emoji, name, description;

    EnumHelpCategory(String emoji, String name, String description) {
        this.emoji = emoji;
        this.name = name;
        this.description = description;
    }

    public String getEmoji() {
        return emoji;
    }

    public String getName() {
        return LocaleHandler.getString(name);
    }

    public String getDescription() {
        return LocaleHandler.getString(description);
    }

    public static EnumHelpCategory[] getItems() {
        return Arrays.stream(values()).filter(enumHelpCategory -> enumHelpCategory != EnumHelpCategory.NONE).toArray(EnumHelpCategory[]::new);
    }
}
