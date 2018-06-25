package by.yarik.yarikweather.app;

import android.app.Application;

import by.yarik.yarikweather.R;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class WeatherApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Montserrat-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
