package com.werp.sero.common.util;

import com.werp.sero.production.exception.WorkOrderInvalidWorkTimeException;
import com.werp.sero.production.exception.WorkOrderWorkTimeRequiredException;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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

    public static YearMonth parseYearMonth(String month) {
        if (month == null || month.isBlank()) {
            throw new InvalidMonthFormatException();
        }

        try {
            return YearMonth.parse(month, DateTimeFormatter.ofPattern("yyyy-MM"));
        } catch (DateTimeParseException e) {
            throw new InvalidMonthFormatException();
        }
    }

    public static int minutesBetween(String start, String end) {

        if (start == null || end == null) {
            throw new WorkOrderWorkTimeRequiredException();
        }

        try {
            LocalDateTime s = LocalDateTime.parse(start, DATE_TIME_SECOND_FORMATTER);
            LocalDateTime e = LocalDateTime.parse(end, DATE_TIME_SECOND_FORMATTER);

            if (e.isBefore(s)) {
                throw new WorkOrderInvalidWorkTimeException();
            }

            return (int) Duration.between(s, e).toMinutes();

        } catch (DateTimeParseException e) {
            throw new WorkOrderInvalidWorkTimeException();
        }
    }

}