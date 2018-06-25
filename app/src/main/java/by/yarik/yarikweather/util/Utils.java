package by.yarik.yarikweather.util;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import by.yarik.yarikweather.R;

public class Utils {

    public static final String TAG = "Utils_logs";

    public static IntentFilter getIntentFilter(String action) {
        IntentFilter intentFilter = new IntentFilter(action);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        return intentFilter;
    }

    public static int getDrawableByWeatherCode(Context context, String code) {
        String[] weatherValuesServer = context.getResources().getStringArray(R.array.weather_values_server);
        //int[] weatherValuesDrawable = context.getResources().getIntArray(R.array.weather_values_drawable);
        TypedArray drawable = context.getResources().obtainTypedArray(R.array.weather_values_drawable);

        for(int i = 0; i < weatherValuesServer.length; i++) {
            if(code.contains(weatherValuesServer[i])) {
                if(i == 0 && code.contains("n")) {
                    return R.drawable.moon;
                } else if(i == 1 && code.contains("n")) {
                    return R.drawable.night_cloud;
                }
                return drawable.getResourceId(i, R.drawable.sun);
            }
        }
        return R.drawable.sun;
    }
}
