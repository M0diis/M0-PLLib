package me.m0dii.pllib.utils;

import java.util.Random;

public class Utils {
    private static final Random random = new Random();

    public static boolean isOnCooldown(long cooldown, long lastUsed) {
        return System.currentTimeMillis() - lastUsed < cooldown;
    }

    public static boolean isValidPlayerName(String in) {
        return in.matches("[a-zA-Z0-9_]{3,16}");
    }

    public static String arabicToRoman(int level) {
        return switch (level) {
            case 1 -> "I";
            case 2 -> "II";
            case 3 -> "III";
            case 4 -> "IV";
            case 5 -> "V";
            case 6 -> "VI";
            case 7 -> "VII";
            case 8 -> "VIII";
            case 9 -> "IX";
            case 10 -> "X";
            case 11 -> "XI";
            case 12 -> "XII";
            case 13 -> "XIII";
            case 14 -> "XIV";
            case 15 -> "XV";
            case 16 -> "XVI";
            case 17 -> "XVII";
            case 18 -> "XVIII";
            case 19 -> "XIX";
            case 20 -> "XX";
            default -> "XX+";
        };
    }

    public static String romanToArabic(String roman) {
        return switch (roman.toUpperCase()) {
            case "I" -> "1";
            case "II" -> "2";
            case "III" -> "3";
            case "IV" -> "4";
            case "V" -> "5";
            case "VI" -> "6";
            case "VII" -> "7";
            case "VIII" -> "8";
            case "IX" -> "9";
            case "X" -> "10";
            case "XI" -> "11";
            case "XII" -> "12";
            case "XIII" -> "13";
            case "XIV" -> "14";
            case "XV" -> "15";
            case "XVI" -> "16";
            case "XVII" -> "17";
            case "XVIII" -> "18";
            case "XIX" -> "19";
            case "XX" -> "20";
            default -> roman;
        };
    }
}
