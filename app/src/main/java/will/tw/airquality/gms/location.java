package will.tw.airquality.gms;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.List;
import java.util.Locale;

import will.tw.airquality.SplashActivity;

/**
 * Created by william on 2017/1/5.
 */

public class location implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private Double lon, lat;
    private String Addresscode;
    public static GoogleApiClient mGoogleApiClient;
    public Location mLastLocation;
    public List<Address> lstAddress;
    public MyLocationCallBack callBack;
//    private SplashActivity activity;

    private Context activity;


    public void init (Context activity) {
        this.activity = activity;
    }

    public void connect(){
        mGoogleApiClient.connect();
    }

    public void disconnect(){
        mGoogleApiClient.disconnect();
    }

    public void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(activity)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        // 這行指令在 IDE 會出現紅線，不過仍可正常執行，可不予理會
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        try {

            if (mLastLocation != null) {
                lat = mLastLocation.getLatitude();
                lon = mLastLocation.getLongitude();
                Log.e("google service :", lat + ":" + lon);
                Geocoder gc = new Geocoder(activity, Locale.getDefault());    //Ša°Ï:¥xÆW
                lstAddress = gc.getFromLocation(lat, lon, 1);
                Addresscode = lstAddress.get(0).getAdminArea();
                if (Addresscode.compareTo("台北市") == 0) {
                    Addresscode = "臺北市";
                } else if (Addresscode.compareTo("台東市") == 0) {
                    Addresscode = "臺東市";
                } else if (Addresscode.compareTo("台東縣") == 0) {
                    Addresscode = "臺東縣";
                } else if (Addresscode.compareTo("台南市") == 0) {
                    Addresscode = "臺南市";
                } else if (Addresscode.compareTo("台中市") == 0) {
                    Addresscode = "臺中市";
                } else {
                    Addresscode = lstAddress.get(0).getAdminArea();
                }
                Log.e("new google Geocode :", Addresscode);


            } else {
                Toast.makeText(activity, "偵測不到定位，請確認定位功能已開啟。", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        callBack.done(Addresscode,lat,lon);
//        callBack.latlondone(lat,lon);
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult result) {
        Log.i("GMS Service error", "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());

    }


    public void addLinstner(MyLocationCallBack Linstner){
        callBack = Linstner;
    }


    public interface MyLocationCallBack{
        void done(String CityName,Double Lat, Double Lon);
//        void latlondone(Double Lat, Double Lon);
    }





}
