package com.twitterapp.searchmicroservice.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateTimeUtil {

    public static Date convertLocalDateTimeToDate(LocalDateTime localDateTime) {
        return Date
                .from(localDateTime.atZone(ZoneId.systemDefault())
                        .toInstant());
    }
}
