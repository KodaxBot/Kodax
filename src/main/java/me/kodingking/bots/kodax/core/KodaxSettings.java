package me.kodingking.bots.kodax.core;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class KodaxSettings {

    private File configFile;
    private boolean debug = false;

    private JsonObject configObject;

    public void configure() throws Exception {
        configObject = new JsonParser().parse(new FileReader(configFile)).getAsJsonObject();
    }

    public File getConfigFile() {
        return configFile;
    }

    public void setConfigFile(File configFile) {
        this.configFile = configFile;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public String getToken() {
        return configObject.has("token") ? configObject.get("token").getAsString() : "";
    }

    public String getLocale() {
        return configObject.has("locale") ? configObject.get("locale").getAsString() : "en_US";
    }

    public boolean isSassMode() {
        return configObject.has("sassMode") && configObject.get("sassMode").getAsBoolean();
    }

    public String[] getBotOwners() {
        if (!configObject.has("owners")) {
            return new String[0];
        }
        List<String> owners = new ArrayList<>();
        configObject.getAsJsonArray("owners").forEach(jsonElement -> owners.add(jsonElement.getAsString()));
        return owners.toArray(new String[0]);
    }

    public String[] getBotAdmins() {
        if (!configObject.has("admins")) {
            return new String[0];
        }
        List<String> admins = new ArrayList<>();
        configObject.getAsJsonArray("admins").forEach(jsonElement -> admins.add(jsonElement.getAsString()));
        return admins.toArray(new String[0]);
    }
}
