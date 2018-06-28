package by.yarik.yarikweather.app;

import android.app.Application;

import by.yarik.yarikweather.R;
import by.yarik.yarikweather.util.CustomSharedPreference;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class WeatherApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Montserrat-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
        if(CustomSharedPreference.isFirstLaunch(getBaseContext())) {
            CustomSharedPreference.addCityInList(getBaseContext(), "Moscow");
            CustomSharedPreference.addCityInList(getBaseContext(), "Saint Petersburg");
            CustomSharedPreference.setIsFirstLaunch(getBaseContext());
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
