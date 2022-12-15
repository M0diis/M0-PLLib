package me.m0dii.pllib.data;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.List;

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

    public File getFile() {
        return file;
    }

    public FileConfiguration getFileCfg() {
        return fileCfg;
    }

    public void set(String path, Object value) {
        this.fileCfg.set(path, value);
    }

    public Object get(String path) {
        return this.fileCfg.get(path);
    }

    public String getString(String path) {
        return this.fileCfg.getString(path);
    }

    public int getInt(String path) {
        return this.fileCfg.getInt(path);
    }

    public double getDouble(String path) {
        return this.fileCfg.getDouble(path);
    }

    public boolean getBoolean(String path) {
        return this.fileCfg.getBoolean(path);
    }

    public List<String> getStringList(String path) {
        return this.fileCfg.getStringList(path);
    }

    public List<Integer> getIntegerList(String path) {
        return this.fileCfg.getIntegerList(path);
    }

    public List<Double> getDoubleList(String path) {
        return this.fileCfg.getDoubleList(path);
    }

    public List<Boolean> getBooleanList(String path) {
        return this.fileCfg.getBooleanList(path);
    }

    public List<?> getList(String path) {
        return this.fileCfg.getList(path);
    }

    public boolean contains(String path) {
        return this.fileCfg.contains(path);
    }

    public boolean isString(String path) {
        return this.fileCfg.isString(path);
    }

    public boolean isInt(String path) {
        return this.fileCfg.isInt(path);
    }

    public boolean isDouble(String path) {
        return this.fileCfg.isDouble(path);
    }

    public boolean isBoolean(String path) {
        return this.fileCfg.isBoolean(path);
    }

    public boolean isList(String path) {
        return this.fileCfg.isList(path);
    }

    public boolean isConfigurationSection(String path) {
        return this.fileCfg.isConfigurationSection(path);
    }

    public boolean isSet(String path) {
        return this.fileCfg.isSet(path);
    }

    public void addDefault(String path, Object value) {
        this.fileCfg.addDefault(path, value);
    }

    public void setDefaults(FileConfiguration defaults) {
        this.fileCfg.setDefaults(defaults);
    }
}