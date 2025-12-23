package com.werp.sero.common.util;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {
    private static final DateTimeFormatter DATE_FORMATTER
            = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATE_TIME_FORMATTER
            = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter DATE_TIME_SECOND_FORMATTER
            = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String nowDate() {
        return LocalDateTime.now().format(DATE_FORMATTER);
    }

    public static String nowDateTime() {
        return LocalDateTime.now().format(DATE_TIME_FORMATTER);
    }

    public static String nowDateTimeSecond() {
        return LocalDateTime.now().format(DATE_TIME_SECOND_FORMATTER);
    }

    public static LocalDate parse(String date) {
        return LocalDate.parse(date); // yyyy-MM-dd
    }

    public static int minutesBetween(String start, String end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("작업 시작/종료 시간이 필요합니다.");
        }

        LocalDateTime s = LocalDateTime.parse(start, DATE_TIME_SECOND_FORMATTER);
        LocalDateTime e = LocalDateTime.parse(end, DATE_TIME_SECOND_FORMATTER);

        if (e.isBefore(s)) {
            throw new IllegalArgumentException("종료 시간이 시작 시간보다 빠릅니다.");
        }

        return (int) Duration.between(s, e).toMinutes();
    }

}
