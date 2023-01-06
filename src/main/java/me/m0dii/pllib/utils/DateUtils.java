package me.m0dii.pllib.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String getDateFromMillis(long ms) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(ms);

        return sdf.format(calendar.getTime());
    }

    public static String getDateFromMillis(String ms) {
        final Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(Long.parseLong(ms));

        return sdf.format(calendar.getTime());
    }

    public static String getDateFromMillis(String ms, SimpleDateFormat format) {
        final Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(Long.parseLong(ms));

        return format.format(calendar.getTime());
    }

    public static String formatInstant(Instant instant, SimpleDateFormat format) {
        if (instant == null) {
            return "-";
        }

        final Date date = Date.from(instant);

        return format.format(date);
    }

    public static String formatInstant(Instant instant) {
        if (instant == null) {
            return "-";
        }

        final Date date = Date.from(instant);

        return sdf.format(date);
    }

    public static String getTimeFromStamp(long time) {
        final Date date = new Date(time);

        return df.format(date);
    }

    public static String getTimeFromStamp(long time, DateFormat format) {
        final Date date = new Date(time);

        return format.format(date);
    }

    public static String getCurrentDate() {
        final LocalDateTime now = LocalDateTime.now();

        return df.format(now);
    }

    public static String getCurrentDate(SimpleDateFormat format) {
        final LocalDateTime now = LocalDateTime.now();

        return format.format(now);
    }
}
