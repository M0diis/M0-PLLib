package me.m0dii.pllib.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;

public class NumberUtils {
    private static final Random random = new Random();

    private static final NumberFormat formatter = new DecimalFormat("#0.00");

    public static String formatDouble(double db) {
        return formatter.format(db);
    }

    public static String formatDouble(double db, String format) {
        final NumberFormat formatter = new DecimalFormat(format);

        return formatter.format(db);
    }

    public static String formatDouble(double db, int decimalPlaces) {
        final NumberFormat formatter = new DecimalFormat("#0." + "0".repeat(decimalPlaces));

        return formatter.format(db);
    }

    public static boolean isDigit(String text) {
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


}
