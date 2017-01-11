package will.tw.airquality.accuweather.model;

/**
 * Created by william on 2017/1/10.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Minimum {

    @SerializedName("Value")
    @Expose
    private String value;
    @SerializedName("Unit")
    @Expose
    private String unit;
    @SerializedName("UnitType")
    @Expose
    private String unitType;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

}