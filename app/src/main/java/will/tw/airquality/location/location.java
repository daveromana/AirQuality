package will.tw.airquality.location;

import java.util.List;
import java.util.Locale;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import will.tw.airquality.MainActivity;

import static android.content.Context.LOCATION_SERVICE;

public class location implements LocationListener {
    /**
     * Called when the activity is first created.
     */
    private boolean getService = false;        //¬O§_€w¶}±Ò©wŠìªA°È

    private MainActivity activity;

    public static Double mLongitude;
    public static Double mLatitude;

    public location(MainActivity activity) {
        this.activity = activity;
    }


    public String testLocationProvider() {
        //šú±ošt²Î©wŠìªA°È
        LocationManager status = (LocationManager) (activity.getSystemService(LOCATION_SERVICE));
        if (status.isProviderEnabled(LocationManager.GPS_PROVIDER) || status.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            //ŠpªGGPS©Îºôžô©wŠì¶}±Ò¡A©I¥slocationServiceInitial()§ó·sŠìžm
            getService = true;    //œT»{¶}±Ò©wŠìªA°È
            return locationServiceInitial();

        } else {
            Toast.makeText(activity, "請開啟定位服務", Toast.LENGTH_LONG).show();
            activity.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));    //¶}±Ò³]©w­¶­±
            return "";
        }
    }

    private LocationManager lms;
    private String bestProvider = LocationManager.GPS_PROVIDER;    //³ÌšÎžê°TŽ£šÑªÌ

    private String locationServiceInitial() {
        lms = (LocationManager) activity.getSystemService(LOCATION_SERVICE);    //šú±ošt²Î©wŠìªA°È
        Criteria criteria = new Criteria();    //žê°TŽ£šÑªÌ¿ïšúŒÐ·Ç
        bestProvider = lms.getBestProvider(criteria, true);    //¿ïŸÜºë·Ç«×³Ì°ªªºŽ£šÑªÌ
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return "";
        }
        Location location = lms.getLastKnownLocation(bestProvider);
//        getLocation(location);
        return getLocation(location);
    }

    private String getLocation(Location location) {    //±N©wŠìžê°TÅã¥ÜŠbµe­±€€
        String returnAddress = "";
        try {
            if (location != null) {
                mLongitude = location.getLongitude();    //šú±ožg«×
                mLatitude = location.getLatitude();    //šú±oœn«×

                Geocoder gc = new Geocoder(activity, Locale.getDefault());    //Ša°Ï:¥xÆW
                List<Address> lstAddress = gc.getFromLocation(mLatitude, mLongitude, 1);
                returnAddress = lstAddress.get(0).getAdminArea();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (returnAddress.compareTo("台北市") == 0) {
            returnAddress = "臺北市";
            return returnAddress;
        } else if (returnAddress.compareTo("台東市") == 0) {
            returnAddress = "臺東市";
            return returnAddress;
        } else if (returnAddress.compareTo("台東縣") == 0) {
            returnAddress = "臺東縣";
            return returnAddress;
        } else if (returnAddress.compareTo("台南市") == 0) {
            returnAddress = "臺南市";
            return returnAddress;
        } else if (returnAddress.compareTo("台中市") == 0) {
            returnAddress = "臺中市";
            return returnAddress;
        } else {
            return returnAddress;

        }

    }


    @Override
    public void onLocationChanged(Location location) {    //·íŠaÂI§ïÅÜ®É
        // TODO Auto-generated method stub
        getLocation(location);
    }

    @Override
    public void onProviderDisabled(String arg0) {    //·íGPS©Îºôžô©wŠì¥\¯àÃö³¬®É
        // TODO Auto-generated method stub
    }

    @Override
    public void onProviderEnabled(String arg0) {    //·íGPS©Îºôžô©wŠì¥\¯à¶}±Ò
        // TODO Auto-generated method stub
    }

    @Override
    public void onStatusChanged(String arg0, int arg1, Bundle arg2) {    //©wŠìª¬ºA§ïÅÜ
        // TODO Auto-generated method stub
    }
//
//    public String getAddressByLocation(Location location) {
//        String returnAddress = "";
//        try {
//            if (location != null) {
//                Double longitude = location.getLongitude();    //šú±ožg«×
//                Double latitude = location.getLatitude();    //šú±oœn«×
//
//                //«Ø¥ßGeocoderª«¥ó: Android 8 ¥H€WŒÒºÃŸ¹ŽúŠ¡·|¥¢±Ñ
//                Geocoder gc = new Geocoder(activity, Locale.TRADITIONAL_CHINESE);    //Ša°Ï:¥xÆW
//                //ŠÛžgœn«×šú±oŠa§}
//                List<Address> lstAddress = gc.getFromLocation(latitude, longitude, 1);
//
//                //	if (!Geocoder.isPresent()){ //Since: API Level 9
//                //		returnAddress = "Sorry! Geocoder service not Present.";
//                //	}
//                returnAddress = lstAddress.get(0).getLocality();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return returnAddress;
//    }
}