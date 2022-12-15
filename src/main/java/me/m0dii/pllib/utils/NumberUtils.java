package me.m0dii.pllib.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class NumberUtils {
    private static final NumberFormat formatter = new DecimalFormat("#0.00");

    public static String formatDouble(double db) {
        return formatter.format(db);
    }

    public static String formatDouble(double db, String format) {
        final NumberFormat formatter = new DecimalFormat(format);

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
}
