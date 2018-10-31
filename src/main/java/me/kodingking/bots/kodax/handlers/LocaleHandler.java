package me.kodingking.bots.kodax.handlers;

import java.util.Locale;
import java.util.ResourceBundle;
import me.kodingking.bots.kodax.Kodax;

public class LocaleHandler {

    private static Locale LOCALE = new Locale("en", "US");
    private static ResourceBundle MESSAGES;

    public static void init() {
        LOCALE = Locale.forLanguageTag(Kodax.SETTINGS.getLocale());
        MESSAGES = ResourceBundle.getBundle("assets.localization.locale", LOCALE);
    }

    public static String getString(String key) {
        return MESSAGES.containsKey(key) ? MESSAGES.getString(key) : key;
    }

}
