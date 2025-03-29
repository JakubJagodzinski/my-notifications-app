package common;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DatetimeParser {

    public static final String DATE_FORMAT = "dd.MM.yyyy HH:mm:ss";
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

    public static String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        return dateFormat.format(new Date());
    }

    public static long parseDateToMillis(String date) {
        LocalDateTime sendBackTime = LocalDateTime.parse(date, dateFormatter);
        Instant instant = sendBackTime.atZone(ZoneId.systemDefault()).toInstant();
        return instant.toEpochMilli();
    }

    public static long getTimeDiff(long timeInMillis) {
        return System.currentTimeMillis() - timeInMillis;
    }

    public static String parseToTime(long timeInMillis) {
        long hr = timeInMillis / 3_600_000;
        long min = (timeInMillis % 3_600_000) / 60_000;
        long s = ((timeInMillis % 3_600_000) % 60_000) / 1_000;
        return hr + " hours, " + min + " minutes, " + s + " seconds";
    }

    public static String parseToDate(long timeInMillis) {
        Instant instant = Instant.ofEpochMilli(timeInMillis);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return localDateTime.format(dateFormatter);
    }

}
