package me.m0dii.pllib.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;

public class NumberUtils {
    private static final Random random = new Random();

    private static final NumberFormat formatter = new DecimalFormat("#0.00");

    public static String formatDouble(@Nullable Double db) {
        return db == null ? "" : formatter.format(db);
    }

    public static String formatDouble(@Nullable Double db, @NotNull String format) {
        if(db == null) {
            return "";
        }

        final NumberFormat formatter = new DecimalFormat(format);

        return formatter.format(db);
    }

    private String formatBigDecimal(@Nullable BigDecimal b) {
        if(b == null) {
            return "";
        }

        DecimalFormat df = new DecimalFormat();

        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(0);
        df.setGroupingUsed(false);

        return df.format(b);
    }

    public static String formatDouble(@Nullable Double db, int decimalPlaces) {
        if(db == null) {
            return "";
        }

        final NumberFormat formatter = new DecimalFormat("#0." + "0".repeat(decimalPlaces));

        return formatter.format(db);
    }

    public static boolean isDigit(@Nullable String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }

        for (char ch : text.toCharArray()) {
            if (!Character.isDigit(ch)) {
                return false;
            }
        }

        return true;
    }

    public static int randomInt(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    public static double randomDouble(double min, double max) {
        return random.nextDouble() * (max - min) + min;
    }

    public static float getRandomInRange(float min, float max) {
        if (min == max) { return min; }
        float range = max-min;

        return (float) (Math.random() * range) + min;
    }

    public static double getRandomInRange(double min, double max) {
        if (min == max) { return min; }
        double range = max-min;

        return Math.random() * range + min;
    }

    public static int getRandomInRange(int min, int max) {
        if (min == max) { return min; }
        int range = max-min;

        return (int) (Math.random() * range) + min;
    }

    public static int parseOr(String value, Runnable runnable) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            runnable.run();
        }

        return 0;
    }

    public static int parseOrDefault(String value, int def) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return def;
        }
    }
}
