package me.m0dii.pllib;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;

public class UpdateChecker {
    private final JavaPlugin plugin;
    private final int resourceId;

    public UpdateChecker(JavaPlugin plugin, int resourceId) {
        this.plugin = plugin;
        this.resourceId = resourceId;
    }

    public void getVersion(@NotNull final Consumer<String> consumer) {
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () ->
        {
            try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + this.resourceId)
                    .openStream();

                 Scanner scanner = new Scanner(inputStream)) {

                if (scanner.hasNext()) {
                    consumer.accept(scanner.next());
                }
            } catch (IOException ex) {
                plugin.getLogger().warning("Failed to check for updates.");
            }
        });
    }

    public void isOutdated(@NotNull final Consumer<Boolean> consumer) {
        getVersion(version ->
        {
            String curr = plugin.getDescription().getVersion();

            if (!curr.equalsIgnoreCase(version.replace("v", ""))) {
                consumer.accept(true);
            }

            consumer.accept(false);
        });
    }

    public void download() {
        try {
            String url = "https://api.spiget.org/v2/resources/ " + resourceId + " /download";
            URL url1 = new URL(url);
            BufferedInputStream in = new BufferedInputStream(url1.openStream());
            FileOutputStream fileOutputStream = new FileOutputStream("plugins" + File.separator + plugin.getName() + ".jar");
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}