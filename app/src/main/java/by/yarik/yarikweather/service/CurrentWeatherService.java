package by.yarik.yarikweather.service;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import by.yarik.yarikweather.api.Api;
import by.yarik.yarikweather.api.pojo.CurrentWeather;

public class CurrentWeatherService extends IntentService {

    public static final String TAG = "CurrentWeatherServic_log";
    public static final String ACTION = "by.yarik.yarikweather.service.CurrentWeatherService";
    public static final String CITY = "CITY";

    @SuppressLint("LongLogTag")
    public CurrentWeatherService() {
        super(TAG);
        Log.d(TAG, ACTION);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        CurrentWeather response = Api.getCurrentWeather(this, intent.getStringExtra(CITY));
        Intent responseIntent = new Intent();
        responseIntent.setAction(ACTION);
        responseIntent.addCategory(Intent.CATEGORY_DEFAULT);
        responseIntent.putExtra(ACTION, response);
        sendBroadcast(responseIntent);
    }

    public static Intent getIntent(Context context, String city) {
        Intent intent = new Intent(context, CurrentWeatherService.class);
        intent.putExtra(CITY, city);
        return intent;
    }
}
