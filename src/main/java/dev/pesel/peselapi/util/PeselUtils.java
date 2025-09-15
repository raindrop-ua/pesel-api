package dev.pesel.peselapi.util;

import dev.pesel.peselapi.exception.InvalidBirthDateException;
import dev.pesel.peselapi.exception.InvalidDateRangeException;

import java.time.LocalDate;

public final class PeselUtils {
    private PeselUtils() {}

    public static final int MIN_YEAR = 1800;
    public static final int MAX_YEAR = 2299;

    public static boolean isLeap(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    public static boolean isValidDate(int year, int month, int day) {
        if (year < MIN_YEAR || year > MAX_YEAR) return false;
        if (month < 1 || month > 12) return false;
        int[] dim = {0,31,28,31,30,31,30,31,31,30,31,30,31};
        int maxDay = (month == 2 && isLeap(year)) ? 29 : dim[month];
        return day >= 1 && day <= maxDay;
    }

    public static void ensureValidDate(LocalDate date) {
        if (date.getYear() < MIN_YEAR || date.getYear() > MAX_YEAR)
            throw new InvalidDateRangeException("PESEL supports only years between 1800 and 2299.");
        if (!isValidDate(date.getYear(), date.getMonthValue(), date.getDayOfMonth()))
            throw new InvalidBirthDateException("Provided birth date is invalid.");
    }

    public static int checksumDigit(String firstTenDigits) {
        int[] w = {1,3,7,9,1,3,7,9,1,3};
        int sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += (firstTenDigits.charAt(i) - '0') * w[i];
        }
        int mod = sum % 10;
        return (10 - mod) % 10;
    }

    public static int monthWithCentury(LocalDate date) {
        int y = date.getYear();
        int m = date.getMonthValue();
        if (y >= 1800 && y <= 1899) return m + 80;
        if (y >= 1900 && y <= 1999) return m;
        if (y >= 2000 && y <= 2099) return m + 20;
        if (y >= 2100 && y <= 2199) return m + 40;
        if (y >= 2200 && y <= 2299) return m + 60;
        throw new InvalidDateRangeException("PESEL supports only years between 1800 and 2299.");
    }
}
