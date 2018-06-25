package by.yarik.yarikweather.util;

import android.content.Context;

import by.yarik.yarikweather.R;

public class StringUtils {

    public static String getAppId(Context context) {
        return context.getResources().getString(R.string.api_key);
    }
}
