package will.tw.airquality.station.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by william on 2016/12/28.
 */

public class StationReport {
    @SerializedName("SiteName")
    @Expose
    private String siteName;
    @SerializedName("SiteEngName")
    @Expose
    private String siteEngName;
    @SerializedName("AreaName")
    @Expose
    private String areaName;
    @SerializedName("County")
    @Expose
    private String county;
    @SerializedName("Township")
    @Expose
    private String township;
    @SerializedName("SiteAddress")
    @Expose
    private String siteAddress;
    @SerializedName("TWD97Lon")
    @Expose
    private String tWD97Lon;
    @SerializedName("TWD97Lat")
    @Expose
    private String tWD97Lat;
    @SerializedName("SiteType")
    @Expose
    private String siteType;

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getSiteEngName() {
        return siteEngName;
    }

    public void setSiteEngName(String siteEngName) {
        this.siteEngName = siteEngName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getTownship() {
        return township;
    }

    public void setTownship(String township) {
        this.township = township;
    }

    public String getSiteAddress() {
        return siteAddress;
    }

    public void setSiteAddress(String siteAddress) {
        this.siteAddress = siteAddress;
    }

    public String getTWD97Lon() {
        return tWD97Lon;
    }

    public void setTWD97Lon(String tWD97Lon) {
        this.tWD97Lon = tWD97Lon;
    }

    public String getTWD97Lat() {
        return tWD97Lat;
    }

    public void setTWD97Lat(String tWD97Lat) {
        this.tWD97Lat = tWD97Lat;
    }

    public String getSiteType() {
        return siteType;
    }

    public void setSiteType(String siteType) {
        this.siteType = siteType;
    }
}
