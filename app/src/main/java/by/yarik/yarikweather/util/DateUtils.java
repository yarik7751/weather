package by.yarik.yarikweather.util;

import android.content.Context;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import by.yarik.yarikweather.R;

public class DateUtils {

    public static final String DATE_FORMAT = "dd.MM.yyyy (EEEE)";
    public static final String DATE_FORMAT_LIST = "dd.MM.yyyy\n(EEEE)";

    public static String getDateByStr(Date date, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
        return dateFormat.format(date);
    }

    public static long getHour(long timestamp) {
        Date date = new Date(timestamp);
        return Long.valueOf(getDateByStr(date, "HH"));
    }

    public static String getCurrentDateByStr() {
        Date currentDate = new Date(System.currentTimeMillis());
        return getDateByStr(currentDate, DATE_FORMAT);
    }

    public static int getResByDayOfWeek(Context context) {
        Date currentDate = new Date(System.currentTimeMillis());
        DateFormat dateFormat = new SimpleDateFormat("HH");
        int hour = Integer.parseInt(dateFormat.format(currentDate));
        if(hour >= 0 && hour < 6) {
            return R.drawable.night_bg;
        } else if(hour >= 6 && hour < 12) {
            return R.drawable.morning_bg;
        } else if(hour >= 12 && hour < 18) {
            return R.drawable.noon_bg;
        } else {
            return R.drawable.evening_bg;
        }
    }
}
