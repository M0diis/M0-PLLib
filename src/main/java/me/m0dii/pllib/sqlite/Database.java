package me.m0dii.pllib.sqlite;

import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class Database {
    protected Connection connection;

    protected final JavaPlugin instance;

    protected Database(JavaPlugin instance) {
        this.instance = instance;
        this.connection = null;
    }

    public abstract void openConnection() throws SQLException, ClassNotFoundException;

    public boolean checkConnection() throws SQLException {
        return (this.connection != null && !this.connection.isClosed());
    }

    public Connection getConnection() {
        return this.connection;
    }

    public void closeConnection() throws SQLException {
        if (this.connection == null)
            return;

        this.connection.close();

    }

    public ResultSet querySQL(String query) throws SQLException, ClassNotFoundException {
        if (!checkConnection())
            openConnection();

        Statement statement = this.connection.createStatement();

        return statement.executeQuery(query);
    }

    public int updateSQL(String query) throws SQLException, ClassNotFoundException {
        if (!checkConnection())
            openConnection();

        Statement statement = this.connection.createStatement();

        return statement.executeUpdate(query);
    }
}