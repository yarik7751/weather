package by.yarik.yarikweather.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import by.yarik.yarikweather.R;
import by.yarik.yarikweather.api.Api;
import by.yarik.yarikweather.api.pojo.CurrentWeather;
import by.yarik.yarikweather.service.CurrentWeatherService;
import by.yarik.yarikweather.ui.activity.MainActivity;
import by.yarik.yarikweather.ui.adapter.CitiesAdapter;
import by.yarik.yarikweather.ui.fragment.base.BaseFragment;
import by.yarik.yarikweather.util.AndroidUtils;
import by.yarik.yarikweather.util.CustomSharedPreference;
import by.yarik.yarikweather.util.Utils;

public class BeginDataFragment extends BaseFragment implements CitiesAdapter.OnCityListener {

    @BindView(R.id.et_city) EditText etCity;
    @BindView(R.id.rv_cities) RecyclerView rvCities;

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

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvCities.setLayoutManager(layoutManager);
        List<String> cities = CustomSharedPreference.getAllCities(getContext());
        CitiesAdapter adapter = new CitiesAdapter(getContext(), cities, this);
        rvCities.setAdapter(adapter);
    }

    @Override
    public void onStop() {
        super.onStop();
        unregisterReceivers();
    }

    private void registerReceivers() {
    }

    private void unregisterReceivers() {
    }

    @OnClick(R.id.btn_set_info)
    public void setInfo() {
        String city = etCity.getText().toString();
        etCity.setText("");
        loadCityInfo(city, true);
    }

    private void loadCityInfo(String city, boolean addInList) {
        AndroidUtils.hideKeyboard(getActivity());
        if(!AndroidUtils.isNetworkOnline(getContext())) {
            Toast.makeText(getContext(), R.string.no_connection, Toast.LENGTH_SHORT).show();
            return;
        }
        if(addInList) {
            CustomSharedPreference.addCityInList(getContext(), city);
        }
        if(city != null && !city.isEmpty()) {
            CustomSharedPreference.setCity(getContext(), city);
            openWeatherFragment();
        } else {
            Toast.makeText(getContext(), R.string.wrong_data, Toast.LENGTH_SHORT).show();
        }
    }

    private void openWeatherFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        if(fragmentManager.getBackStackEntryCount() != 0) {
            ((MainActivity) getActivity()).setFabVisibility(View.VISIBLE);
            fragmentManager.popBackStack();
        } else {
            ((MainActivity) getActivity()).setWeatherFragment();
        }
    }

    @Override
    public void onCity(String city) {
        loadCityInfo(city, false);
    }
}
