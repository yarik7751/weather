package by.yarik.yarikweather.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import by.yarik.yarikweather.api.pojo.CurrentWeather;

public class CustomSharedPreference {

    public static final String TAG = "CS_Preference_logs";
    public static final String SHARED_PREFERENCES_TITLE = "sp_currency_by_yarik";
    public static final String SP_CITY = "SP_CITY";
    public static final String SP_CURRENT_WEATHER = "SP_CURRENT_WEATHER";

    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(SHARED_PREFERENCES_TITLE, Context.MODE_PRIVATE);
    }

    public static void setCity(Context context, String city) {
        getSharedPreferences(context).edit().putString(SP_CITY, city).apply();
    }

    public static String getCity(Context context) {
        return getSharedPreferences(context).getString(SP_CITY, null);
    }

    public static void setCurrentWeather(Context context, CurrentWeather currentWeather) {
        Gson gson = new Gson();
        String currentWeatherInfoString = gson.toJson(currentWeather, CurrentWeather.class).toString();
        Log.d(TAG, "currentWeatherInfoString: " + currentWeatherInfoString);
        getSharedPreferences(context).edit().putString(SP_CURRENT_WEATHER, currentWeatherInfoString).apply();
    }

    public static CurrentWeather getCurrentWeather(Context context) {
        String currentWeatherInfoString = getSharedPreferences(context).getString(SP_CURRENT_WEATHER, null);
        Gson gson = new Gson();
        return gson.fromJson(currentWeatherInfoString, CurrentWeather.class);
    }
}
