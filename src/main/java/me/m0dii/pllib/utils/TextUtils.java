package me.m0dii.pllib.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class TextUtils {
    private static final MiniMessage USER_MESSAGE_MINI_MESSAGE = MiniMessage.miniMessage();
    public static final Pattern DEFAULT_URL_PATTERN = Pattern.compile("(?:(https?)://)?([-\\w_.]+\\.\\w{2,})(/\\S*)?");
    public static final Pattern URL_SCHEME_PATTERN = Pattern.compile("^[a-z][a-z\\d+\\-.]*:");

    private static final TextReplacementConfig URL_REPLACER_CONFIG = TextReplacementConfig.builder()
            .match(DEFAULT_URL_PATTERN)
            .replacement(builder -> {
                String clickUrl = builder.content();
                if (!URL_SCHEME_PATTERN.matcher(clickUrl).find()) {
                    clickUrl = "https://" + clickUrl;
                }
                return builder.clickEvent(ClickEvent.openUrl(clickUrl));
            })
            .build();

    public static Component kyorify(@NotNull String message) {
        return USER_MESSAGE_MINI_MESSAGE.deserialize(Kyorifier.kyorify(message)).replaceText(URL_REPLACER_CONFIG);
    }

    private static final Pattern HEX_PATTERN = Pattern.compile("&#([A-Fa-f0-9])([A-Fa-f0-9])([A-Fa-f0-9])([A-Fa-f0-9])([A-Fa-f0-9])([A-Fa-f0-9])");

    public static String stripColor(Component component) {
        return ChatColor.stripColor(PlainTextComponentSerializer.plainText().serializeOr(component, ""));
    }

    public static String stripColor(String text) {
        return ChatColor.stripColor(text);
    }

    public static Component colorize(String text) {
        if (text == null || text.isEmpty()) {
            return Component.empty();
        }

        return LegacyComponentSerializer.legacyAmpersand().deserialize(text)
                .asComponent()
                .decoration(TextDecoration.ITALIC, false);
    }

    private static final Map<String, Character> SMALL_CAPS_MAP = Map.ofEntries(
            Map.entry("a", 'ᴀ'),
            Map.entry("b", 'ʙ'),
            Map.entry("c", 'ᴄ'),
            Map.entry("d", 'ᴅ'),
            Map.entry("e", 'ᴇ'),
            Map.entry("f", 'ғ'),
            Map.entry("g", 'ɢ'),
            Map.entry("h", 'ʜ'),
            Map.entry("i", 'ɪ'),
            Map.entry("j", 'ᴊ'),
            Map.entry("k", 'ᴋ'),
            Map.entry("l", 'ʟ'),
            Map.entry("m", 'ᴍ'),
            Map.entry("n", 'ɴ'),
            Map.entry("o", 'ᴏ'),
            Map.entry("p", 'ᴘ'),
            Map.entry("q", 'ǫ'),
            Map.entry("r", 'ʀ'),
            Map.entry("s", 's'),
            Map.entry("t", 'ᴛ'),
            Map.entry("u", 'ᴜ'),
            Map.entry("v", 'ᴠ'),
            Map.entry("w", 'ᴡ'),
            Map.entry("x", 'x'),
            Map.entry("y", 'ʏ'),
            Map.entry("z", 'ᴢ')
    );

    public static String smallCaps(String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (char c : text.toCharArray()) {
            String lowerC = String.valueOf(c).toLowerCase();
            if (SMALL_CAPS_MAP.containsKey(lowerC)) {
                sb.append(SMALL_CAPS_MAP.get(lowerC));
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
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

        if (lore != null) {
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

    public static List<String> splitTextToLines(String message, int maxWordsInLine) {
        StringBuilder sb = new StringBuilder();

        String[] words = message.split(" ");

        List<String> lines = new ArrayList<>();

        for (int i = 0; i < words.length; i++) {
            if (i % maxWordsInLine == 0 && i != 0) {
                lines.add(sb.toString());

                sb = new StringBuilder();
            }

            sb.append(words[i]).append(" ");
        }

        lines.add(sb.toString().trim());

        return lines;
    }

    public static List<String> splitTextToLines(String message) {
        return splitTextToLines(message, 6);
    }
}