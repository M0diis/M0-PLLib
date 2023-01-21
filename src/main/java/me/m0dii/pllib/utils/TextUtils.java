package me.m0dii.pllib.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class TextUtils {
    private static final Pattern HEX_PATTERN = Pattern.compile("&#([A-Fa-f0-9])([A-Fa-f0-9])([A-Fa-f0-9])([A-Fa-f0-9])([A-Fa-f0-9])([A-Fa-f0-9])");

    public static String stripColor(Component component) {
        return ChatColor.stripColor(PlainTextComponentSerializer.plainText().serializeOr(component, ""));
    }

    public static TextComponent colorize(String text) {
        if (text == null || text.isEmpty()) {
            return Component.empty();
        }

        return Component.text(ChatColor.translateAlternateColorCodes(
                '&',
                HEX_PATTERN.matcher(text).replaceAll("&x&$1&$2&$3&$4&$5&$6")
        ));
    }

    public static ItemMeta displayName(ItemMeta meta, String text) {
        if (meta == null) {
            return null;
        }

        meta.displayName(colorize(text));

        return meta;
    }

    public static ItemMeta lore(ItemMeta meta, String... text) {
        List<Component> lore = new ArrayList<>();

        for (String s : text) {
            lore.add(colorize(s));
        }

        if (meta != null) {
            meta.lore(lore);
        }

        return meta;
    }

    public static ItemMeta lore(ItemMeta meta, List<String> text) {
        List<Component> lore = new ArrayList<>();

        for (String s : text) {
            lore.add(colorize(s));
        }

        if (meta != null) {
            meta.lore(lore);
        }

        return meta;
    }

    public static ItemMeta lore(ItemMeta meta, boolean add, String... text) {
        List<Component> lore;

        if (add) {
            lore = meta.lore();
        } else {
            lore = new ArrayList<>();
        }

        if(lore != null) {
            for (String s : text) {
                lore.add(colorize(s));
            }
        }

        if (meta != null) {
            meta.lore(lore);
        }

        return meta;
    }

    public static String format(String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }

        return ChatColor.translateAlternateColorCodes(
                '&',
                HEX_PATTERN.matcher(text).replaceAll("&x&$1&$2&$3&$4&$5&$6")
        );
    }

    public static String formatted(String text, String... args) {
        if (text == null || text.isEmpty()) {
            return "";
        }

        for (int i = 0; i < args.length; i++) {
            text = text.replace("{" + i + "}", args[i]);
        }

        return format(text);
    }
}