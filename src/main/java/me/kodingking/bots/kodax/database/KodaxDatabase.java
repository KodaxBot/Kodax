package me.kodingking.bots.kodax.database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Statement;
import me.kodingking.bots.kodax.handlers.LoggingHandler;

public class KodaxDatabase {
    
    public static Connection MAIN_DATABASE;
    
    private static File DATA_DIR = new File("data");
    private static File DATABASE_DIR = new File(DATA_DIR, "databases");
    
    public static void init() {
        if (!DATA_DIR.exists()) {
            DATA_DIR.mkdirs();
        }
        if (!DATABASE_DIR.exists()) {
            DATABASE_DIR.mkdirs();
        }
        
        MAIN_DATABASE = createNewDatabase("Kodax.db");
    }
    
    private static Connection createNewDatabase(String fileName) {
        try {
            return DriverManager.getConnection("jdbc:sqlite:" + new File(DATABASE_DIR, fileName));
        } catch (SQLException e) {
            LoggingHandler.error(e);
        }
        return null;
    }

    public static String getSetting(String key, String defaultStr) {
        try {
            Statement tableStatement = MAIN_DATABASE.createStatement();
            tableStatement.execute("CREATE TABLE IF NOT EXISTS settings(key varchar(255) UNIQUE, value varchar(255))");

            PreparedStatement insertStatement = MAIN_DATABASE.prepareStatement("INSERT OR IGNORE INTO settings(key, value) VALUES (?, ?)");
            insertStatement.setString(1, key);
            insertStatement.setString(2, defaultStr);
            insertStatement.execute();

            PreparedStatement resultStatement = MAIN_DATABASE.prepareStatement("SELECT * FROM `settings` WHERE `key` LIKE ?");
            resultStatement.setString(1, key);

            ResultSet resultSet = resultStatement.executeQuery();
            return resultSet.getString(2);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void setSetting(String key, String value) {
        try {
            Statement tableStatement = MAIN_DATABASE.createStatement();
            tableStatement.execute("CREATE TABLE IF NOT EXISTS settings(key varchar(255) UNIQUE, value varchar(255))");

            PreparedStatement updateStatement = MAIN_DATABASE.prepareStatement("UPDATE settings SET value = ? WHERE key = ?");
            updateStatement.setString(1, value);
            updateStatement.setString(2, key);
            updateStatement.executeUpdate();

            PreparedStatement insertStatement = MAIN_DATABASE.prepareStatement("INSERT OR IGNORE INTO settings (key, value) VALUES (?, ?)");
            insertStatement.setString(1, key);
            insertStatement.setString(2, value);
            insertStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
