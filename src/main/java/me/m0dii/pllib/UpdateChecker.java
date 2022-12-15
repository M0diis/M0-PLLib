package me.m0dii.pllib;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Consumer;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class UpdateChecker {
    private final JavaPlugin instance;
    private final int resourceId;

    public UpdateChecker(JavaPlugin plugin, int resourceId) {
        this.instance = plugin;
        this.resourceId = resourceId;
    }

    public void getVersion(final Consumer<String> consumer) {
        Bukkit.getScheduler().runTaskAsynchronously(this.instance, () ->
        {
            try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + this.resourceId)
                    .openStream();

                 Scanner scanner = new Scanner(inputStream)) {

                if (scanner.hasNext()) {
                    consumer.accept(scanner.next());
                }
            } catch (IOException ex) {
                instance.getLogger().warning("Failed to check for updates.");
            }
        });
    }

    public void isOutdated(final Consumer<Boolean> consumer) {
        getVersion(version ->
        {
            String curr = instance.getDescription().getVersion();

            if (!curr.equalsIgnoreCase(version.replace("v", ""))) {
                consumer.accept(true);
            }

            consumer.accept(false);
        });
    }
}