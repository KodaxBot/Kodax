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
            tableStatement.execute(
                "CREATE TABLE IF NOT EXISTS settings(key varchar(255) UNIQUE, value varchar(255))");

            PreparedStatement insertStatement = MAIN_DATABASE
                .prepareStatement("INSERT OR IGNORE INTO settings(key, value) VALUES (?, ?)");
            insertStatement.setString(1, key);
            insertStatement.setString(2, defaultStr);
            insertStatement.execute();

            PreparedStatement resultStatement = MAIN_DATABASE
                .prepareStatement("SELECT * FROM `settings` WHERE `key` LIKE ?");
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
            tableStatement.execute(
                "CREATE TABLE IF NOT EXISTS settings(key varchar(255) UNIQUE, value varchar(255))");

            PreparedStatement updateStatement = MAIN_DATABASE
                .prepareStatement("UPDATE settings SET value = ? WHERE key = ?");
            updateStatement.setString(1, value);
            updateStatement.setString(2, key);
            updateStatement.executeUpdate();

            PreparedStatement insertStatement = MAIN_DATABASE
                .prepareStatement("INSERT OR IGNORE INTO settings (key, value) VALUES (?, ?)");
            insertStatement.setString(1, key);
            insertStatement.setString(2, value);
            insertStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object getUserValue(String id, String column, Object defaultObj) {
        try {
            Statement tableStatement = MAIN_DATABASE.createStatement();
            tableStatement.execute("CREATE TABLE IF NOT EXISTS users(id varchar(255) UNIQUE)");

            PreparedStatement insertStatement = MAIN_DATABASE
                .prepareStatement("INSERT OR IGNORE INTO users(id) VALUES (?)");
            insertStatement.setString(1, id);
            insertStatement.execute();

            Statement columnStatement = MAIN_DATABASE.createStatement();
            ResultSet columnResult = columnStatement.executeQuery("PRAGMA table_info (users)");
            boolean exists = false;
            while (columnResult.next()) {
                if (columnResult.getString(2).equalsIgnoreCase(column)) {
                    exists = true;
                    break;
                }
            }

            if (!exists) {
                PreparedStatement tableAlterStatement = MAIN_DATABASE
                    .prepareStatement("ALTER TABLE users ADD COLUMN " + column + " varchar(255)");
                tableAlterStatement.execute();

                PreparedStatement updateStatement = MAIN_DATABASE
                    .prepareStatement("UPDATE users SET " + column + " = ? WHERE id = ?");
                updateStatement.setObject(1, defaultObj);
                updateStatement.setString(2, id);
                updateStatement.executeUpdate();
            }

            PreparedStatement resultStatement = MAIN_DATABASE
                .prepareStatement("SELECT " + column + " FROM users WHERE id = ?");
            resultStatement.setString(1, id);

            ResultSet resultSet = resultStatement.executeQuery();
            return resultSet.getObject(column);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }

    public static void setUserValue(String id, String column, Object value) {
        try {
            getUserValue(id, column, value);

            PreparedStatement updateStatement = MAIN_DATABASE
                .prepareStatement("UPDATE users SET " + column + " = ? WHERE id = ?");
            updateStatement.setObject(1, value);
            updateStatement.setString(2, id);
            updateStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
