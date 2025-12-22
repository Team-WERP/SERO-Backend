package com.werp.sero.common.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {
    private static final DateTimeFormatter DATE_FORMATTER
            = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATE_TIME_FORMATTER
            = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static String nowDate() {
        return LocalDateTime.now().format(DATE_FORMATTER);
    }

    public static String nowDateTime() {
        return LocalDateTime.now().format(DATE_TIME_FORMATTER);
    }

    public static LocalDate parse(String date) {
        return LocalDate.parse(date); // yyyy-MM-dd
    }
}
