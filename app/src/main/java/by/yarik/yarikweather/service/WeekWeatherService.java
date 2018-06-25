package by.yarik.yarikweather.service;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import by.yarik.yarikweather.api.Api;
import by.yarik.yarikweather.api.pojo.CurrentWeather;
import by.yarik.yarikweather.api.pojo.WeekWeather;

public class WeekWeatherService extends IntentService {

    public static final String TAG = "WeekWeatherServic_log";
    public static final String ACTION = "by.yarik.yarikweather.service.WeekWeatherService";
    public static final String CITY = "CITY";

    @SuppressLint("LongLogTag")
    public WeekWeatherService() {
        super(TAG);
        Log.d(TAG, ACTION);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        WeekWeather response = Api.getWeekWeather(this, intent.getStringExtra(CITY));
        Intent responseIntent = new Intent();
        responseIntent.setAction(ACTION);
        responseIntent.addCategory(Intent.CATEGORY_DEFAULT);
        responseIntent.putExtra(ACTION, response);
        sendBroadcast(responseIntent);
    }

    public static Intent getIntent(Context context, String city) {
        Intent intent = new Intent(context, WeekWeatherService.class);
        intent.putExtra(CITY, city);
        return intent;
    }
}
