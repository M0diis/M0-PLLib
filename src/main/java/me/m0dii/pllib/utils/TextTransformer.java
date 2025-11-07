package me.m0dii.pllib.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.md_5.bungee.api.ChatColor;
import org.checkerframework.checker.units.qual.N;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.regex.Pattern;

public class TextTransformer {
    private static final MiniMessage USER_MESSAGE_MINI_MESSAGE = MiniMessage.miniMessage();
    private static final Pattern DEFAULT_URL_PATTERN = Pattern.compile("(?:(https?)://)?([-\\w_.]+\\.\\w{2,})(/\\S*)?");
    private static final Pattern URL_SCHEME_PATTERN = Pattern.compile("^[a-z][a-z\\d+\\-.]*:");
    private static final Pattern HEX_PATTERN = Pattern.compile("&#([A-Fa-f0-9])([A-Fa-f0-9])([A-Fa-f0-9])([A-Fa-f0-9])([A-Fa-f0-9])([A-Fa-f0-9])");

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

    private String text;

    public TextTransformer(@NotNull String text) {
        this.text = text;
    }

    public static TextTransformer of(@NotNull String text) {
        return new TextTransformer(text);
    }

    @NotNull
    public TextTransformer upperCase() {
        this.text = this.text.toUpperCase();
        return this;
    }

    @NotNull
    public TextTransformer lowerCase() {
        this.text = this.text.toLowerCase();
        return this;
    }

    @NotNull
    public TextTransformer trim() {
        this.text = this.text.trim();
        return this;
    }

    @NotNull
    public TextTransformer applyColor() {
        if (text == null || text.isEmpty()) {
            return this;
        }

        text = ChatColor.translateAlternateColorCodes(
                '&',
                HEX_PATTERN.matcher(text).replaceAll("&x&$1&$2&$3&$4&$5&$6")
        );

        return this;
    }

    @NotNull
    public TextTransformer stripColor() {
        text = ChatColor.stripColor(text);

        return this;
    }

    @NotNull
    public TextTransformer smallCaps() {
        if (text == null || text.isEmpty()) {
            return this;
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

        text = sb.toString();

        return this;
    }

    @NotNull
    public TextTransformer replace(@NotNull String target, @NotNull String replacement) {
        this.text = this.text.replace(target, replacement);
        return this;
    }

    @NotNull
    public TextTransformer replace(@NotNull Map<String, Object> replacements) {
        for (Map.Entry<String, Object> entry : replacements.entrySet()) {
            this.text = this.text.replace(entry.getKey(), String.valueOf(entry.getValue()));
        }
        return this;
    }

    @NotNull
    public TextTransformer substring(int beginIndex, int endIndex) {
        this.text = this.text.substring(beginIndex, endIndex);
        return this;
    }

    @NotNull
    public TextTransformer substring(int beginIndex) {
        this.text = this.text.substring(beginIndex);
        return this;
    }

    @NotNull
    public TextTransformer append(@NotNull String suffix) {
        this.text = this.text + suffix;
        return this;
    }

    @NotNull
    public TextTransformer prepend(@NotNull String prefix) {
        this.text = prefix + this.text;
        return this;
    }

    @NotNull
    public TextTransformer repeat(int count) {
        this.text = String.valueOf(this.text).repeat(Math.max(0, count));
        return this;
    }

    @NotNull
    public TextTransformer reverse() {
        this.text = new StringBuilder(this.text).reverse().toString();
        return this;
    }

    @NotNull
    public TextTransformer clear() {
        this.text = "";
        return this;
    }

    @NotNull
    public TextTransformer set(@NotNull String newText) {
        this.text = newText;
        return this;
    }

    @NotNull
    public String get() {
        return this.text;
    }

    @NotNull
    public Component kyorify() {
        return USER_MESSAGE_MINI_MESSAGE.deserialize(Kyorifier.kyorify(text)).replaceText(URL_REPLACER_CONFIG);
    }

    @NotNull
    public Component colorize() {
        if (text == null || text.isEmpty()) {
            return Component.empty();
        }

        return LegacyComponentSerializer.legacyAmpersand().deserialize(text)
                .asComponent()
                .decoration(TextDecoration.ITALIC, false);
    }
}
