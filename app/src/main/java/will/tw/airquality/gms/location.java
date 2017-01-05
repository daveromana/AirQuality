package will.tw.airquality.gms;

import android.Manifest;
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

    public static Double lon,lat;
    public static String Addresscode;
    public static GoogleApiClient mGoogleApiClient;
    public Location mLastLocation;
    private SplashActivity activity;





    public void buildGoogleApiClient()
    {
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

            if (mLastLocation != null)
            {
                lat = mLastLocation.getLatitude();
                lon = mLastLocation.getLongitude();
                Log.e("google service :" , lat+":"+lon);
                Geocoder gc = new Geocoder(activity, Locale.getDefault());    //Ša°Ï:¥xÆW
                List<Address> lstAddress = gc.getFromLocation(lat, lon, 1);
                Addresscode = lstAddress.get(0).getAdminArea();
                Log.e("google Geocode :" , Addresscode);
            }
            else
            {
                Toast.makeText(activity, "偵測不到定位，請確認定位功能已開啟。", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult result) {
        Log.i("GMS Service error", "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());

    }
}
