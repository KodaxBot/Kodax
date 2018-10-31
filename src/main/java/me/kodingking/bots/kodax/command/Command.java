package me.kodingking.bots.kodax.command;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import me.kodingking.bots.kodax.command.help.EnumHelpCategory;
import me.kodingking.bots.kodax.command.permission.EnumPermission;

@Retention(RetentionPolicy.RUNTIME)
public @interface Command {

    String name();

    String usage();

    String description() default "commands.general.noDescription";

    String[] examples() default {};

    EnumPermission permission() default EnumPermission.NONE;

    EnumHelpCategory category();

}
