package will.tw.airquality.uv.model;

/**
 * Created by william on 2016/12/28.
 */
import com.google.gson.annotations.SerializedName;

public class UvReport {
    @SerializedName("SiteName")
    
    private String siteName;
    @SerializedName("UVI")
    
    private String uVI;
    @SerializedName("PublishAgency")
    
    private String publishAgency;
    @SerializedName("County")
    
    private String county;
    @SerializedName("WGS84Lon")
    
    private String wGS84Lon;
    @SerializedName("WGS84Lat")
    
    private String wGS84Lat;
    @SerializedName("PublishTime")
    
    private String publishTime;

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getUVI() {
        return uVI;
    }

    public void setUVI(String uVI) {
        this.uVI = uVI;
    }

    public String getPublishAgency() {
        return publishAgency;
    }

    public void setPublishAgency(String publishAgency) {
        this.publishAgency = publishAgency;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getWGS84Lon() {
        return wGS84Lon;
    }

    public void setWGS84Lon(String wGS84Lon) {
        this.wGS84Lon = wGS84Lon;
    }

    public String getWGS84Lat() {
        return wGS84Lat;
    }

    public void setWGS84Lat(String wGS84Lat) {
        this.wGS84Lat = wGS84Lat;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }
}
