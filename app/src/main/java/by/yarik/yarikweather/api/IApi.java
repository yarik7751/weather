package by.yarik.yarikweather.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IApi {

    @GET("/data/2.5/weather")
    Call<ResponseBody> getCurrentWeather( @Query("appid") String appid, @Query("q") String q);

    @GET("/data/2.5/forecast")
    Call<ResponseBody> getWeekWeather( @Query("appid") String appid, @Query("q") String q);
}
