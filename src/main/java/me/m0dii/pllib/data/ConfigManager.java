package me.m0dii.pllib.data;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

public class ConfigManager {
    private final JavaPlugin instance;
    private final String configFile = "config.yml";
    private FileConfiguration dataConfig = null;
    private File dataConfigFile = null;

    public ConfigManager(JavaPlugin instance) {
        this.instance = instance;

        this.saveDefaultConfig();
    }

    public void reloadConfig() {
        if (this.dataConfigFile == null) {
            this.dataConfigFile = new File(this.instance.getDataFolder(), this.configFile);
        }

        this.dataConfig = YamlConfiguration.loadConfiguration(this.dataConfigFile);

        InputStream defConfigStream = this.instance.getResource(this.configFile);

        if (defConfigStream == null) {
            return;
        }

        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream));

        this.dataConfig.setDefaults(defConfig);
    }

    public FileConfiguration getConfig() {
        if (this.dataConfig == null) {
            reloadConfig();
        }

        return this.dataConfig;
    }

    public void saveConfig() {
        if (this.dataConfig == null || this.dataConfigFile == null) {
            return;
        }

        try {
            this.getConfig().save(this.dataConfigFile);
        } catch (IOException ex) {
            this.instance.getLogger().log(Level.SEVERE, "Could not save config to " + this.dataConfigFile, ex);
        }
    }

    public void saveDefaultConfig() {
        if (this.dataConfigFile == null) {
            this.dataConfigFile = new File(this.instance.getDataFolder(), this.configFile);
        }

        if (!this.dataConfigFile.exists()) {
            this.instance.saveResource(this.configFile, false);
        }
    }
}