
package by.yarik.yarikweather.api.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class WeekWeather implements Parcelable {

    @SerializedName("cod")
    @Expose
    private String cod;
    @SerializedName("message")
    @Expose
    private Double message;
    @SerializedName("cnt")
    @Expose
    private Integer cnt;
    @SerializedName("list")
    @Expose
    private java.util.List<WeatherList> list = null;
    @SerializedName("city")
    @Expose
    private City city;

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public WeekWeather withCod(String cod) {
        this.cod = cod;
        return this;
    }

    public Double getMessage() {
        return message;
    }

    public void setMessage(Double message) {
        this.message = message;
    }

    public WeekWeather withMessage(Double message) {
        this.message = message;
        return this;
    }

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    public WeekWeather withCnt(Integer cnt) {
        this.cnt = cnt;
        return this;
    }

    public java.util.List<WeatherList> getWeatherList() {
        return list;
    }

    public void setWeatherList(java.util.List<WeatherList> list) {
        this.list = list;
    }

    public WeekWeather withList(java.util.List<WeatherList> list) {
        this.list = list;
        return this;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public WeekWeather withCity(City city) {
        this.city = city;
        return this;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.cod);
        dest.writeValue(this.message);
        dest.writeValue(this.cnt);
        dest.writeList(this.list);
        dest.writeParcelable(this.city, flags);
    }

    public WeekWeather() {
    }

    protected WeekWeather(Parcel in) {
        this.cod = in.readString();
        this.message = (Double) in.readValue(Double.class.getClassLoader());
        this.cnt = (Integer) in.readValue(Integer.class.getClassLoader());
        this.list = new ArrayList<WeatherList>();
        in.readList(this.list, WeatherList.class.getClassLoader());
        this.city = in.readParcelable(City.class.getClassLoader());
    }

    public static final Parcelable.Creator<WeekWeather> CREATOR = new Parcelable.Creator<WeekWeather>() {
        @Override
        public WeekWeather createFromParcel(Parcel source) {
            return new WeekWeather(source);
        }

        @Override
        public WeekWeather[] newArray(int size) {
            return new WeekWeather[size];
        }
    };
}
