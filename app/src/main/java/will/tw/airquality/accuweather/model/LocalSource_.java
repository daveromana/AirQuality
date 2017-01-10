package will.tw.airquality.accuweather.model;

/**
 * Created by william on 2017/1/10.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocalSource_ {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("WeatherCode")
    @Expose
    private String weatherCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeatherCode() {
        return weatherCode;
    }

    public void setWeatherCode(String weatherCode) {
        this.weatherCode = weatherCode;
    }

}