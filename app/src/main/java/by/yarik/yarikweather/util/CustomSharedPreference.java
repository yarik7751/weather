package by.yarik.yarikweather.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import by.yarik.yarikweather.api.pojo.CurrentWeather;

public class CustomSharedPreference {

    public static final String TAG = "CS_Preference_logs";
    public static final String SHARED_PREFERENCES_TITLE = "sp_currency_by_yarik";
    public static final String SP_CITY = "SP_CITY";
    public static final String SP_CITY_LIST = "SP_CITY_LIST";
    public static final String SP_IS_FIRST = "SP_IS_FIRST";
    public static final String SP_CURRENT_WEATHER = "SP_CURRENT_WEATHER";

    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(SHARED_PREFERENCES_TITLE, Context.MODE_PRIVATE);
    }

    public static boolean isFirstLaunch(Context context) {
        return getSharedPreferences(context).getBoolean(SP_IS_FIRST, true);
    }

    public static void setIsFirstLaunch(Context context) {
        getSharedPreferences(context).edit().putBoolean(SP_IS_FIRST, false).apply();
    }

    public static void setCity(Context context, String city) {
        city.trim();
        getSharedPreferences(context).edit().putString(SP_CITY, city).apply();
    }

    public static String getCity(Context context) {
        return getSharedPreferences(context).getString(SP_CITY, null);
    }

    public static void addCityInList(Context context, String city) {
        city.trim();
        List<String> cities = getAllCities(context);
        if(cities == null || cities.size() == 0) {
            cities = new ArrayList<String>();
        }
        if(!isExistCityInList(cities, city)) {
            cities.add(city);
            getSharedPreferences(context).edit().putString(SP_CITY_LIST, StringUtils.listToString(cities)).apply();
        }
    }

    public static List<String> getAllCities(Context context) {
        String citiesStr = getSharedPreferences(context).getString(SP_CITY_LIST, null);
        if(TextUtils.isEmpty(citiesStr)) {
            return null;
        } else {
            return StringUtils.StringToList(citiesStr);
        }
    }

    private static boolean isExistCityInList(List<String> cities, String city) {
        city.trim();
        for(int i = 0; i < cities.size(); i++) {
            String listCity = cities.get(i).trim();
            if(city.equalsIgnoreCase(listCity)) {
                return true;
            }
        }
        return false;
    }
}
