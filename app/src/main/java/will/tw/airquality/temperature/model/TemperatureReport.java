package will.tw.airquality.temperature.model;

/**
 * Created by Ashbar on 2017/1/11.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class TemperatureReport {

    @SerializedName("DateTime")
    @Expose
    private String dateTime;
    @SerializedName("EpochDateTime")
    @Expose
    private String epochDateTime;
    @SerializedName("WeatherIcon")
    @Expose
    private String weatherIcon;
    @SerializedName("IconPhrase")
    @Expose
    private String iconPhrase;
    @SerializedName("IsDaylight")
    @Expose
    private String isDaylight;
    @SerializedName("Temperature")
    @Expose
    private Temperature temperature;
    @SerializedName("PrecipitationProbability")
    @Expose
    private String precipitationProbability;
    @SerializedName("MobileLink")
    @Expose
    private String mobileLink;
    @SerializedName("Link")
    @Expose
    private String link;

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getEpochDateTime() {
        return epochDateTime;
    }

    public void setEpochDateTime(String epochDateTime) {
        this.epochDateTime = epochDateTime;
    }

    public String getWeatherIcon() {
        return weatherIcon;
    }

    public void setWeatherIcon(String weatherIcon) {
        this.weatherIcon = weatherIcon;
    }

    public String getIconPhrase() {
        return iconPhrase;
    }

    public void setIconPhrase(String iconPhrase) {
        this.iconPhrase = iconPhrase;
    }

    public String getIsDaylight() {
        return isDaylight;
    }

    public void setIsDaylight(String isDaylight) {
        this.isDaylight = isDaylight;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    public String getPrecipitationProbability() {
        return precipitationProbability;
    }

    public void setPrecipitationProbability(String precipitationProbability) {
        this.precipitationProbability = precipitationProbability;
    }

    public String getMobileLink() {
        return mobileLink;
    }

    public void setMobileLink(String mobileLink) {
        this.mobileLink = mobileLink;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
