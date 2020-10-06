package com.example.myapp.loca;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static String format(LocalDate localDate) {
        return localDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
    }
}
