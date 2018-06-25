package by.yarik.yarikweather.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import by.yarik.yarikweather.R;
import by.yarik.yarikweather.api.Api;
import by.yarik.yarikweather.api.pojo.CurrentWeather;
import by.yarik.yarikweather.service.CurrentWeatherService;
import by.yarik.yarikweather.ui.activity.MainActivity;
import by.yarik.yarikweather.ui.fragment.base.BaseFragment;
import by.yarik.yarikweather.util.AndroidUtils;
import by.yarik.yarikweather.util.CustomSharedPreference;
import by.yarik.yarikweather.util.Utils;

public class BeginDataFragment extends BaseFragment {

    @BindView(R.id.et_city) EditText etCity;

    private GetCurrentWeatherReceiver getCurrentWeatherReceiver;

    private static BeginDataFragment beginDataFragment;
    public static BeginDataFragment getInstance() {
        if(beginDataFragment == null) {
            beginDataFragment = new BeginDataFragment();
        }

        return beginDataFragment;
    }

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
        return inflater.inflate(R.layout.fragment_begin_data, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String city = CustomSharedPreference.getCity(getContext());
        etCity.setText(city != null ? city : "");
    }

    @Override
    public void onStop() {
        super.onStop();
        unregisterReceivers();
    }

    private void registerReceivers() {
        getCurrentWeatherReceiver = new GetCurrentWeatherReceiver();
        getActivity().registerReceiver(getCurrentWeatherReceiver, Utils.getIntentFilter(CurrentWeatherService.ACTION));
    }

    private void unregisterReceivers() {
        getActivity().unregisterReceiver(getCurrentWeatherReceiver);
    }

    @OnClick(R.id.btn_set_info)
    public void setInfo() {
        if(!AndroidUtils.isNetworkOnline(getContext())) {
            Toast.makeText(getContext(), R.string.no_connection, Toast.LENGTH_SHORT).show();
            return;
        }
        String city = etCity.getText().toString();
        if(city != null && !city.isEmpty()) {
            CustomSharedPreference.setCity(getContext(), city);
            getActivity().startService(CurrentWeatherService.getIntent(getContext(), city));
        } else {
            Toast.makeText(getContext(), R.string.wrong_data, Toast.LENGTH_SHORT).show();
        }
    }

    public class GetCurrentWeatherReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            CurrentWeather currentWeather = intent.getParcelableExtra(CurrentWeatherService.ACTION);
            ((MainActivity) getActivity()).setWeatherFragment(currentWeather);
        }
    }
}
