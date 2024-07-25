package me.m0dii.pllib.mysql;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

public abstract class AbstractMySQL {
    private static final String JDBC_PATTERN = "jdbc:mysql://%s:%d/%s?autoReconnect=true";

    protected Connection connection;

    protected final JavaPlugin plugin;

    protected final String host;
    protected final String database;
    protected final String username;
    protected final String password;
    protected final int port;

    protected AbstractMySQL(@NotNull JavaPlugin plugin,
                            @NotNull String host,
                            @NotNull String database,
                            @NotNull String username,
                            @NotNull String password) {
        this(plugin, host, database, username, password, 3306);
    }

    protected AbstractMySQL(@NotNull JavaPlugin plugin,
                            @NotNull String host,
                            @NotNull String database,
                            @NotNull String username,
                            @NotNull String password,
                            int port) {
        this.plugin = plugin;

        this.host = host;
        this.database = database;
        this.username = username;
        this.password = password;
        this.port = port;

        connect();
    }

    protected AbstractMySQL(@NotNull JavaPlugin plugin,
                            @NotNull String jdbcUrl) {
        this.plugin = plugin;

        String[] split = jdbcUrl.split("://");
        String[] split2 = split[1].split("/");
        String[] split3 = split2[0].split(":");

        this.host = split3[0];
        this.port = Integer.parseInt(split3[1]);
        this.database = split2[1];
        this.username = split[0];
        this.password = split[1];

        if(host == null || database == null || username == null || password == null) {
            throw new IllegalArgumentException("Invalid JDBC URL provided.");
        }

        connect();
    }

    protected void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(JDBC_PATTERN.formatted(this.host, this.port, this.database), this.username, this.password);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    protected void checkConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connect();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    protected Connection getConnection() {
        checkConnection();

        return connection;
    }

    protected CompletableFuture<Void> updateAsync(@NotNull String query) {
        return CompletableFuture.runAsync(() -> {
            try (Connection conn = getConnection();
                 PreparedStatement ps = conn.prepareStatement(query)) {
                ps.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
    }

    protected CompletableFuture<Void> updateAsync(@NotNull String query, @Nullable Object... params) {
        return CompletableFuture.runAsync(() -> {
            try (Connection conn = getConnection();
                 PreparedStatement ps = conn.prepareStatement(query)) {

                if (params != null) {
                    for (int i = 0; i < params.length; i++) {
                        Object param = params[i];

                        int index = i + 1;

                        if (param instanceof String strParam) {
                            ps.setString(index, strParam);
                        } else if (param instanceof Integer intParam) {
                            ps.setInt(index, intParam);
                        } else if (param instanceof Long longParam) {
                            ps.setLong(index, longParam);
                        } else if (param instanceof Double doubleParam) {
                            ps.setDouble(index, doubleParam);
                        } else if (param instanceof Float floatParam) {
                            ps.setFloat(index, floatParam);
                        } else if (param instanceof Boolean boolParam) {
                            ps.setBoolean(index, boolParam);
                        } else {
                            ps.setObject(index, param);
                        }
                    }
                }

                ps.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
