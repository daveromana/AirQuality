package will.tw.airquality.accuweather.model;

/**
 * Created by william on 2017/1/10.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Night {

    @SerializedName("Icon")
    @Expose
    private Integer icon;
    @SerializedName("IconPhrase")
    @Expose
    private String iconPhrase;
    @SerializedName("LocalSource")
    @Expose
    private LocalSource_ localSource;

    public Integer getIcon() {
        return icon;
    }

    public void setIcon(Integer icon) {
        this.icon = icon;
    }

    public String getIconPhrase() {
        return iconPhrase;
    }

    public void setIconPhrase(String iconPhrase) {
        this.iconPhrase = iconPhrase;
    }

    public LocalSource_ getLocalSource() {
        return localSource;
    }

    public void setLocalSource(LocalSource_ localSource) {
        this.localSource = localSource;
    }

}