package will.tw.airquality.air.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ashbar on 2017/1/4.
 */

public class Record {

    @SerializedName("SiteName")
    @Expose
    private String siteName;
    @SerializedName("County")
    @Expose
    private String county;
    @SerializedName("PSI")
    @Expose
    private String pSI;
    @SerializedName("MajorPollutant")
    @Expose
    private String majorPollutant;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("SO2")
    @Expose
    private String sO2;
    @SerializedName("CO")
    @Expose
    private String cO;
    @SerializedName("O3")
    @Expose
    private String o3;
    @SerializedName("PM10")
    @Expose
    private String pM10;
    @SerializedName("PM2.5")
    @Expose
    private String pM25;
    @SerializedName("NO2")
    @Expose
    private String nO2;
    @SerializedName("WindSpeed")
    @Expose
    private String windSpeed;
    @SerializedName("WindDirec")
    @Expose
    private String windDirec;
    @SerializedName("FPMI")
    @Expose
    private String fPMI;
    @SerializedName("NOx")
    @Expose
    private String nOx;
    @SerializedName("NO")
    @Expose
    private String nO;
    @SerializedName("PublishTime")
    @Expose
    private String publishTime;

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getPSI() {
        return pSI;
    }

    public void setPSI(String pSI) {
        this.pSI = pSI;
    }

    public String getMajorPollutant() {
        return majorPollutant;
    }

    public void setMajorPollutant(String majorPollutant) {
        this.majorPollutant = majorPollutant;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSO2() {
        return sO2;
    }

    public void setSO2(String sO2) {
        this.sO2 = sO2;
    }

    public String getCO() {
        return cO;
    }

    public void setCO(String cO) {
        this.cO = cO;
    }

    public String getO3() {
        return o3;
    }

    public void setO3(String o3) {
        this.o3 = o3;
    }

    public String getPM10() {
        return pM10;
    }

    public void setPM10(String pM10) {
        this.pM10 = pM10;
    }

    public String getPM25() {
        return pM25;
    }

    public void setPM25(String pM25) {
        this.pM25 = pM25;
    }

    public String getNO2() {
        return nO2;
    }

    public void setNO2(String nO2) {
        this.nO2 = nO2;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWindDirec() {
        return windDirec;
    }

    public void setWindDirec(String windDirec) {
        this.windDirec = windDirec;
    }

    public String getFPMI() {
        return fPMI;
    }

    public void setFPMI(String fPMI) {
        this.fPMI = fPMI;
    }

    public String getNOx() {
        return nOx;
    }

    public void setNOx(String nOx) {
        this.nOx = nOx;
    }

    public String getNO() {
        return nO;
    }

    public void setNO(String nO) {
        this.nO = nO;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }
}
