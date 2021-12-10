package com.geekq.common.utils;


import org.apache.commons.lang3.time.DateUtils;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static Date endOfDay(Date d) {
        return DateUtils.addSeconds(
                DateUtils.addDays(DateUtils.truncate(d, Calendar.DATE), 1), -1);
    }

    public static long getSecondsBetweenDates(Date d1, Date d2) {
        return Math.abs((d1.getTime() - d2.getTime()) / 1000);
    }
}
