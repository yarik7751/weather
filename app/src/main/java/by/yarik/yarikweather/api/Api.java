package by.yarik.yarikweather.api;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import by.yarik.yarikweather.api.pojo.CurrentWeather;
import by.yarik.yarikweather.api.pojo.WeekWeather;
import by.yarik.yarikweather.util.StringUtils;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {

    public static final String TAG = "Api_logs";
    public static final String BASE_URL = "http://api.openweathermap.org/";
    public static final String RESPONSE = "response";
    public static final int CODE_SUCCESS = 200;

    private static IApi iApi;

    public static IApi getApi() {
        if (iApi == null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(50, TimeUnit.SECONDS)
                    .writeTimeout(50, TimeUnit.SECONDS)
                    .readTimeout(50, TimeUnit.SECONDS)
                    .build();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
            iApi = retrofit.create(IApi.class);
            return iApi;
        } else {
            return iApi;
        }
    }

    public static CurrentWeather getCurrentWeather(Context context, String city) {
        Log.d(TAG, "getCurrentWeather");
        Gson gson = new Gson();
        Call<ResponseBody> call = getApi().getCurrentWeather(StringUtils.getAppId(context), city);
        Response<ResponseBody> response;
        String rawJson;
        try {
            response = call.execute();
            if(response != null && response.body() != null) {
                rawJson = response.body().string();
                Log.d(TAG, "rawJson: " + rawJson);
                if(response.code() == CODE_SUCCESS) {
                    return gson.fromJson(rawJson, CurrentWeather.class);
                }
            } else {
                Log.d(TAG, "rawJson: null");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static WeekWeather getWeekWeather(Context context, String city) {
        Log.d(TAG, "getWeekWeather");
        Gson gson = new Gson();
        Call<ResponseBody> call = getApi().getWeekWeather(StringUtils.getAppId(context), city);
        Response<ResponseBody> response;
        String rawJson;
        try {
            response = call.execute();
            if(response != null && response.body() != null) {
                rawJson = response.body().string();
                Log.d(TAG, "rawJson: " + rawJson);
                if(response.code() == CODE_SUCCESS) {
                    return gson.fromJson(rawJson, WeekWeather.class);
                }
            } else {
                Log.d(TAG, "rawJson: null");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
