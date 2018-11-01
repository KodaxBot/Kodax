package me.kodingking.bots.kodax.database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
    
    public static Connection createNewDatabase(String fileName) {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + new File(DATABASE_DIR, fileName))) {
            return conn;
        } catch (SQLException e) {
            LoggingHandler.error(e);
        }
        return null;
    }
    
}
