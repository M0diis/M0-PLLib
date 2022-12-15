package me.m0dii.pllib.data;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class AbstractFile {
    protected final File file;
    protected final FileConfiguration fileCfg;

    public AbstractFile(JavaPlugin plugin, String fileName) {
        this.file = new File(plugin.getDataFolder(), fileName);

        if (!file.exists()) {
            try {
                //noinspection ResultOfMethodCallIgnored
                file.createNewFile();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        this.fileCfg = YamlConfiguration.loadConfiguration(file);
    }

    public void reload() {
        try {
            fileCfg.load(file);
        }
        catch (IOException | InvalidConfigurationException ex) {
            ex.printStackTrace();
        }
    }

    public void save() {
        try {
            if (fileCfg != null) {
                fileCfg.save(file);
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}