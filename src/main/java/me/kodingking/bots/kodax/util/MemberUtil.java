package me.kodingking.bots.kodax.util;

import java.util.Arrays;
import me.kodingking.bots.kodax.Kodax;
import me.kodingking.bots.kodax.command.permission.EnumPermission;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;

public class MemberUtil {

    public static EnumPermission getPermissionLevel(Member member) {
        String[] botOwners = Kodax.SETTINGS.getBotOwners();
        String[] botAdmins = Kodax.SETTINGS.getBotAdmins();

        if (Arrays.asList(botOwners).contains(member.getUser().getId())) {
            return EnumPermission.BOT_OWNER;
        } else if (Arrays.asList(botAdmins).contains(member.getUser().getId())) {
            return EnumPermission.BOT_ADMIN;
        } else if (member.isOwner()) {
            return EnumPermission.SERVER_OWNER;
        } else if (member.hasPermission(Permission.ADMINISTRATOR) || member
            .hasPermission(Permission.MANAGE_SERVER)) {
            return EnumPermission.ADMIN;
        } else if (member.hasPermission(Permission.MESSAGE_MANAGE) || member
            .hasPermission(Permission.KICK_MEMBERS) || member
            .hasPermission(Permission.BAN_MEMBERS)) {
            return EnumPermission.MODERATOR;
        } else {
            return EnumPermission.NONE;
        }
    }
}
