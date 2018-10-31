package me.kodingking.bots.kodax.command.permission;

public enum EnumPermission {

    BOT_OWNER("Bot Owner", "a "),
    BOT_ADMIN("Bot Admin", "a "),
    SERVER_OWNER("Server Owner", "a "),
    ADMIN("Admin", "an "),
    MODERATOR("Mod", "a "),
    NONE("None", "");

    private String displayName;
    private String prefix;

    EnumPermission(String displayName, String prefix) {
        this.displayName = displayName;
        this.prefix = prefix;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getPrefix() {
        return prefix;
    }

}
