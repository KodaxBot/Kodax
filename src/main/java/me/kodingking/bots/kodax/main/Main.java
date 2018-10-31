package me.kodingking.bots.kodax.main;

import java.io.File;
import me.kodingking.bots.kodax.Kodax;
import me.kodingking.bots.kodax.core.KodaxSettings;
import me.kodingking.bots.kodax.handlers.LoggingHandler;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Main {

    public static void main(String[] args) {
        Options options = new Options();

        Option configFile = new Option("c", "config", true, "Config file path");
        configFile.setRequired(true);
        options.addOption(configFile);

        Option debug = new Option("d", "debug", false, "Use debug mode");
        debug.setRequired(false);
        options.addOption(debug);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("Kodax", options);

            System.exit(1);
        }

        String configFileOpt = cmd.getOptionValue("config");
        boolean debugOpt = cmd.hasOption("debug");

        File configFileObj = new File(configFileOpt);
        if (!configFileObj.exists()) {
            System.out.println("Error: Config file path invalid");
            System.exit(1);
        }

        KodaxSettings settings = new KodaxSettings();
        settings.setConfigFile(configFileObj);
        settings.setDebug(debugOpt);

        try {
            settings.configure();
        } catch (Exception e) {
            System.out.println("Error: Could not configure settings");
            LoggingHandler.error(e);
            System.exit(1);
        }

        Kodax.SETTINGS = settings;
        Kodax.INSTANCE = new Kodax();

        try {
            Kodax.INSTANCE.start();
        } catch (Exception e) {
            System.out.println("Error: Could not start bot");
            LoggingHandler.error(e);
            System.exit(1);
        }
    }

}
