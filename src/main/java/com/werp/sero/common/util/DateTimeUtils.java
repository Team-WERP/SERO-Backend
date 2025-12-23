package com.werp.sero.common.util;

import com.werp.sero.production.exception.InvalidMonthFormatException;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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

}
