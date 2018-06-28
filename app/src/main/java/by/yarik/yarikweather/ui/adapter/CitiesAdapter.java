package by.yarik.yarikweather.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import by.yarik.yarikweather.R;

public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.CitiesViewHolder> {

    private Context context;
    private List<String> cities;
    private OnCityListener onCityListener;

    public CitiesAdapter(Context context, List<String> cities, OnCityListener onCityListener) {
        this.context = context;
        this.cities = cities;
        this.onCityListener = onCityListener;
    }

    @Override
    public CitiesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city, parent, false);
        return new CitiesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CitiesViewHolder holder, int position) {
        holder.tvCity.setText(cities.get(position));
        holder.itemView.setOnClickListener((View v) -> {
            onCityListener.onCity(holder.tvCity.getText().toString());
        });
    }

    @Override
    public int getItemCount() {
        return cities == null ? 0 : cities.size();
    }


    public class CitiesViewHolder extends RecyclerView.ViewHolder {

        public TextView tvCity;

        public CitiesViewHolder(View itemView) {
            super(itemView);

            tvCity = (TextView) itemView.findViewById(R.id.tv_city);
        }
    }

    public static interface OnCityListener {
        void onCity(String city);
    }
}
