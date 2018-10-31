package me.kodingking.bots.kodax.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

public class CommandUtil {

    public static String getUsage(HelpFormatter formatter, Options options, String name) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        formatter.printHelp(pw, formatter.getWidth(), name, null, options, formatter.getLeftPadding(), formatter.getDescPadding(), null, false);
        pw.flush();
        return String.valueOf(sw.getBuffer());
    }

    public static String getOptions(HelpFormatter formatter, Options options) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        formatter.printOptions(pw, formatter.getWidth(), options, formatter.getLeftPadding(), formatter.getDescPadding());
        pw.flush();
        return String.valueOf(sw.getBuffer());
    }

}
