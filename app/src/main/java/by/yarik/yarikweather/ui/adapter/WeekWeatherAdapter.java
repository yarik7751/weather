package by.yarik.yarikweather.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import by.yarik.yarikweather.R;
import by.yarik.yarikweather.api.pojo.Weather;
import by.yarik.yarikweather.api.pojo.WeatherList;
import by.yarik.yarikweather.api.pojo.WeekWeather;
import by.yarik.yarikweather.util.DateUtils;
import by.yarik.yarikweather.util.Utils;

public class WeekWeatherAdapter extends RecyclerView.Adapter<WeekWeatherAdapter.WeekWeatherViewHolder> {

    private final int DAY_TIME = 15;
    private final int DAY_COUNT = 3;

    private Context context;
    private List<WeatherList> listWeather;

    public WeekWeatherAdapter(Context context, List<WeatherList> listWeather) {
        this.context = context;
        this.listWeather = listWeather;

        for(int i = listWeather.size() - 1; i >= 0; i--) {
            WeatherList weather = listWeather.get(i);
            long hour = DateUtils.getHour(weather.getDt() * 1000);
            if(hour != DAY_TIME) {
                listWeather.remove(i);
            }
        }
        if(listWeather.size() >= DAY_COUNT) {
            for (int i = listWeather.size() - 1; i >= 0; i--) {
                if(listWeather.size() > DAY_COUNT) {
                    listWeather.remove(i);
                } else {
                    break;
                }
            }
        }
    }

    private void prepareList() {

    }

    @Override
    public WeekWeatherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_week_weather, parent, false);
        return new WeekWeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WeekWeatherViewHolder holder, int position) {
        WeatherList weather = listWeather.get(position);
        int temper = weather.getMain().getTemp().intValue() - 273;
        holder.tvTemper.setText(temper + " C");

        int wind = weather.getWind().getSpeed().intValue();
        holder.tvWind.setText(wind + " m/s");

        int humidity = weather.getMain().getHumidity();
        holder.tvHumidity.setText(humidity + "%");

        String code = weather.getWeather().get(0).getIcon();
        holder.imgWeatherInfo.setImageResource(Utils.getDrawableByWeatherCode(context, code));
        holder.tvWeatherInfo.setText(weather.getWeather().get(0).getDescription());

        long dateLong = weather.getDt() * 1000;
        Date date = new Date(dateLong);
        holder.tvDate.setText(DateUtils.getDateByStr(date, DateUtils.DATE_FORMAT_LIST));
    }

    @Override
    public int getItemCount() {
        return listWeather == null ? 0 : listWeather.size();
    }

    public class WeekWeatherViewHolder extends RecyclerView.ViewHolder {

        public TextView tvDate;
        public TextView tvWeatherInfo;
        public TextView tvWind;
        public TextView tvTemper;
        public TextView tvHumidity;
        public ImageView imgWeatherInfo;

        public WeekWeatherViewHolder(View itemView) {
            super(itemView);

            tvDate = (TextView) itemView.findViewById(R.id.tv_date);
            tvWeatherInfo = (TextView) itemView.findViewById(R.id.tv_weather_info);
            tvWind = (TextView) itemView.findViewById(R.id.tv_wind);
            tvTemper = (TextView) itemView.findViewById(R.id.tv_temper);
            tvHumidity = (TextView) itemView.findViewById(R.id.tv_humidity);
            imgWeatherInfo = (ImageView) itemView.findViewById(R.id.img_weather_info);
        }
    }
}
