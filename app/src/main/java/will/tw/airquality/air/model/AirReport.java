package will.tw.airquality.air.model;

/**
 * Created by william on 2016/12/28.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AirReport {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("result")
    @Expose
    private Result result;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
