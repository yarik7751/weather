package by.yarik.yarikweather.ui.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import butterknife.BindView;
import butterknife.OnClick;
import by.yarik.yarikweather.R;
import by.yarik.yarikweather.api.pojo.CurrentWeather;
import by.yarik.yarikweather.ui.activity.base.BaseActivity;
import by.yarik.yarikweather.ui.fragment.BeginDataFragment;
import by.yarik.yarikweather.ui.fragment.WeatherFragment;
import by.yarik.yarikweather.util.CustomSharedPreference;

public class MainActivity extends BaseActivity {

    @BindView(R.id.fab) FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null) {
            String city = CustomSharedPreference.getCity(this);
            if(city == null) {
                setBeginDataFragment(false);
            } else {
                setWeatherFragment();
            }
        }
    }

    @OnClick(R.id.fab)
    public void changeCity() {
        setBeginDataFragment(true);
    }

    public void setWeatherFragment() {
        fab.setVisibility(View.VISIBLE);
        onSwitchFragment(WeatherFragment.getInstance(), "", false, false, R.id.fl_container);
    }

    public void setBeginDataFragment(boolean add) {
        fab.setVisibility(View.GONE);
        onSwitchFragment(BeginDataFragment.getInstance(), "", add, false, R.id.fl_container);
    }

    public void setFabVisibility(int visibility) {
        fab.setVisibility(visibility);
    }
}
