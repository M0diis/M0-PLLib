package me.m0dii.pllib.sqlite;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteDB extends Database {
    private final String dbLocation;

    public SQLiteDB(JavaPlugin plugin, String dbLocation) {
        super(plugin);

        this.dbLocation = dbLocation;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void openConnection() throws SQLException, ClassNotFoundException {
        if (checkConnection()) {
            return;
        }

        if (!this.instance.getDataFolder().exists()) {
            this.instance.getDataFolder().mkdirs();
        }

        File file = new File(this.instance.getDataFolder(), this.dbLocation);

        if (!file.exists()) {
            try {
                file.createNewFile();
            }
            catch (IOException ex) {
                instance.getLogger().severe("Unable to create the database!");
            }
        }

        Class.forName("org.sqlite.JDBC");

        this.connection = DriverManager.getConnection(
                "jdbc:sqlite:" + this.instance.getDataFolder().toPath() + "/" + this.dbLocation);
    }
}