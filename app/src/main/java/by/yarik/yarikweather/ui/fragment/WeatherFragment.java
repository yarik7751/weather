package by.yarik.yarikweather.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import by.yarik.yarikweather.R;
import by.yarik.yarikweather.api.Api;
import by.yarik.yarikweather.api.pojo.CurrentWeather;
import by.yarik.yarikweather.api.pojo.WeekWeather;
import by.yarik.yarikweather.service.CurrentWeatherService;
import by.yarik.yarikweather.service.WeekWeatherService;
import by.yarik.yarikweather.ui.activity.MainActivity;
import by.yarik.yarikweather.ui.adapter.WeekWeatherAdapter;
import by.yarik.yarikweather.ui.fragment.base.BaseFragment;
import by.yarik.yarikweather.util.AndroidUtils;
import by.yarik.yarikweather.util.CustomSharedPreference;
import by.yarik.yarikweather.util.DateUtils;
import by.yarik.yarikweather.util.DialogUtils;
import by.yarik.yarikweather.util.Utils;

public class WeatherFragment extends BaseFragment {

    @BindView(R.id.tv_city) TextView tvCity;
    @BindView(R.id.tv_date) TextView tvDate;
    @BindView(R.id.tv_weather_info) TextView tvWeatherInfo;
    @BindView(R.id.tv_temper) TextView tvTemper;
    @BindView(R.id.tv_humidity) TextView tvHumidity;
    @BindView(R.id.tv_wind) TextView tvWind;
    @BindView(R.id.ll_top_info) LinearLayout llTopInfo;
    @BindView(R.id.ll_weather_title) LinearLayout llWeatherTitle;
    @BindView(R.id.ll_weather_data) LinearLayout llWeatherData;
    @BindView(R.id.pb_load_week_weather) ProgressBar pbLoadWeekWeather;
    @BindView(R.id.pb_load_current_weather) ProgressBar pbLoadCurrentWeather;
    @BindView(R.id.rv_week_weather) RecyclerView rvWeekWeather;
    @BindView(R.id.img_weather_info) ImageView imgWeatherInfo;

    private static WeatherFragment weatherFragment;
    public static WeatherFragment getInstance() {
        if(weatherFragment == null) {
            weatherFragment = new WeatherFragment();
        }
        return weatherFragment;
    }

    private GetCurrentWeatherReceiver getCurrentWeatherReceiver;
    private GetWeekWeatherReceiver getWeekWeatherReceiver;

    @Override
    public void onStart() {
        super.onStart();
        registerReceivers();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weather, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setTopSize();
        ((MainActivity) getActivity()).setFabVisibility(View.VISIBLE);
        llWeatherTitle.setVisibility(View.INVISIBLE);
        llWeatherData.setVisibility(View.INVISIBLE);
        llTopInfo.setBackgroundResource(DateUtils.getResByDayOfWeek(getContext()));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvWeekWeather.setLayoutManager(layoutManager);

        startCurrentWeatherService();
        startWeekWeatherService();
    }

    private void startCurrentWeatherService() {
        if(AndroidUtils.isNetworkOnline(getContext())) {
            getActivity().startService(CurrentWeatherService.getIntent(getContext(), CustomSharedPreference.getCity(getContext())));
        } else {
            DialogUtils.noConnection(getContext(),
                    (DialogInterface dialog, int which) -> {
                        dialog.cancel();
                        startCurrentWeatherService();
                    },
                    (DialogInterface dialog, int which) -> {
                        getActivity().onBackPressed();
                    }).show();
        }
    }

    private void startWeekWeatherService() {
        if(AndroidUtils.isNetworkOnline(getContext())) {
            getActivity().startService(WeekWeatherService.getIntent(getContext(), CustomSharedPreference.getCity(getContext())));
        } else {
            DialogUtils.noConnection(getContext(),
                    (DialogInterface dialog, int which) -> {
                        dialog.cancel();
                        startWeekWeatherService();
                    },
                    (DialogInterface dialog, int which) -> {
                        getActivity().onBackPressed();
                    }).show();
        }
    }

    private void setCurrentWeatherInfo(CurrentWeather currentWeather) {
        pbLoadCurrentWeather.setVisibility(View.GONE);
        llWeatherTitle.setVisibility(View.VISIBLE);
        llWeatherData.setVisibility(View.VISIBLE);
        tvCity.setText(currentWeather.getName());
        tvDate.setText(DateUtils.getCurrentDateByStr());
        imgWeatherInfo.setImageResource(Utils.getDrawableByWeatherCode(
                getContext(),
                currentWeather.getWeather().get(0).getIcon()
                ));
        tvWeatherInfo.setText(currentWeather.getWeather().get(0).getDescription());
        tvTemper.setText(setTemper(currentWeather));
        tvWind.setText(currentWeather.getWind().getSpeed().intValue() + "m/s");
        tvHumidity.setText(currentWeather.getMain().getHumidity() + "%");
    }

    private void setTopSize() {
        int size = AndroidUtils.getWindowsSizeParams(getContext())[0];
        llTopInfo.getLayoutParams().width = size;
        llTopInfo.getLayoutParams().height = (int) (size * 0.60);
    }

    private String setTemper(CurrentWeather currentWeather) {
        int min = (int) (currentWeather.getMain().getTempMin() - 273);
        int max = (int) (currentWeather.getMain().getTempMax() - 273);
        if(min == max) {
            return max + "C";
        } else {
            return min + "C" + " - " + max + "C";
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        unregisterReceivers();
    }

    private void registerReceivers() {
        getCurrentWeatherReceiver = new GetCurrentWeatherReceiver();
        getActivity().registerReceiver(getCurrentWeatherReceiver, Utils.getIntentFilter(CurrentWeatherService.ACTION));

        getWeekWeatherReceiver = new GetWeekWeatherReceiver();
        getActivity().registerReceiver(getWeekWeatherReceiver, Utils.getIntentFilter(WeekWeatherService.ACTION));
    }

    private void unregisterReceivers() {
        getActivity().unregisterReceiver(getCurrentWeatherReceiver);
        getActivity().unregisterReceiver(getWeekWeatherReceiver);
    }

    public class GetCurrentWeatherReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            pbLoadCurrentWeather.setVisibility(View.GONE);
            CurrentWeather currentWeather = intent.getParcelableExtra(CurrentWeatherService.ACTION);
            setCurrentWeatherInfo(currentWeather);
            //startWeekWeatherService();
        }
    }

    public class GetWeekWeatherReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            pbLoadWeekWeather.setVisibility(View.GONE);

            WeekWeather weekWeather = intent.getParcelableExtra(WeekWeatherService.ACTION);
            WeekWeatherAdapter adapter = new WeekWeatherAdapter(getContext(), weekWeather.getWeatherList());
            rvWeekWeather.setAdapter(adapter);
        }
    }
}
