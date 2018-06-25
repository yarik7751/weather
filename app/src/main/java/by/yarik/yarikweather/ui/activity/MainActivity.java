package by.yarik.yarikweather.ui.activity;

import android.os.Bundle;

import by.yarik.yarikweather.R;
import by.yarik.yarikweather.api.pojo.CurrentWeather;
import by.yarik.yarikweather.ui.activity.base.BaseActivity;
import by.yarik.yarikweather.ui.fragment.BeginDataFragment;
import by.yarik.yarikweather.ui.fragment.WeatherFragment;
import by.yarik.yarikweather.util.CustomSharedPreference;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null) {
            String city = CustomSharedPreference.getCity(this);
            if(city == null) {
                setBeginDataFragment();
            } else {
                setWeatherFragment(null);
            }
        }
    }

    public void setWeatherFragment(CurrentWeather currentWeather) {
        onSwitchFragment(WeatherFragment.getInstance(currentWeather), "", false, false, R.id.fl_container);
    }

    public void setBeginDataFragment() {
        onSwitchFragment(BeginDataFragment.getInstance(), "", false, false, R.id.fl_container);
    }
}