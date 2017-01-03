package will.tw.airquality;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import will.tw.airquality.air.api.AirApi;
import will.tw.airquality.air.model.AirReport;
import will.tw.airquality.station.api.StationApi;
import will.tw.airquality.station.model.Record;
import will.tw.airquality.station.model.StationReport;

/**
 * Created by Ashbar on 2016/12/31.
 */

public class AirService extends Service {
    public static String cityname;

    public static String sitename = "";
    private Handler handler = new Handler();
    public static  ArrayList<will.tw.airquality.air.model.Record> mAirReport;

    private Intent intent;
    private SplashActivity splashActivity;
    public AirService(){


    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        cityname = testLocationProvider();
//        Log.e("William service", cityname);
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

//        station = StationSys("County eq \'"+cityname+"\'");
//        cityname = testLocationProvider();
//        Log.e("William service", cityname);
//        StationSys("County eq \'"+cityname+"\'");
        return super.onStartCommand(intent, flags, startId);


    }

    @Override
    public void onCreate() {
        super.onCreate();
        cityname = testLocationProvider();
        Log.e("William service", cityname);
        StationSys("{County:"+cityname+"}");

//        new Thread() {
//            @Override
//            public void run() {
//                StationSys("County eq \'"+cityname+"\'");
//                super.run();
//            }
//        }.start();
    }

    /**
     * Called when the activity is first created.
     */
    private boolean getService = false;        //¬O§_€w¶}±Ò©wŠìªA°È


    public static Double mLongitude;
    public static Double mLatitude;


    public String testLocationProvider() {
        //šú±ošt²Î©wŠìªA°È
        LocationManager status = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (status.isProviderEnabled(LocationManager.GPS_PROVIDER) || status.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            //ŠpªGGPS©Îºôžô©wŠì¶}±Ò¡A©I¥slocationServiceInitial()§ó·sŠìžm
            getService = true;    //œT»{¶}±Ò©wŠìªA°È
            return locationServiceInitial();

        } else {
            Toast.makeText(this, "請開啟定位服務", Toast.LENGTH_LONG).show();
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));    //¶}±Ò³]©w­¶­±
            return "";
        }
    }

    private LocationManager lms;
    private String bestProvider = LocationManager.GPS_PROVIDER;    //³ÌšÎžê°TŽ£šÑªÌ

    private String locationServiceInitial() {
        lms = (LocationManager) getSystemService(LOCATION_SERVICE);    //šú±ošt²Î©wŠìªA°È
        Criteria criteria = new Criteria();    //žê°TŽ£šÑªÌ¿ïšúŒÐ·Ç
        bestProvider = lms.getBestProvider(criteria, true);    //¿ïŸÜºë·Ç«×³Ì°ªªºŽ£šÑªÌ
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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

                Geocoder gc = new Geocoder(this, Locale.getDefault());    //Ša°Ï:¥xÆW
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


    private class StationSubscriber extends Subscriber<StationReport> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            Log.e("onRrror",e.toString());
            onCreate();
        }

        @Override
        public void onNext(StationReport report) {
            ArrayList<Record> reports = report.getResult().getRecords();
            Double lat;
            Double lon;
            Double nowlat = AirService.mLatitude;
            Double nowlon = AirService.mLongitude;
            Double mindisten = 99999999999999.9;
            for (int i= 0; i<reports.size(); i ++){
                lat = Double.valueOf(reports.get(i).getTWD97Lat());
                lon = Double.valueOf(reports.get(i).getTWD97Lon());
                Double sum = (nowlat - lat)*(nowlat - lat)+(nowlon - lon)*(nowlon - lon);
                Double distence = Math.sqrt(sum);
                if (distence<mindisten){
                    sitename = reports.get(i).getSiteName();
                    mindisten = distence;
                }
//                mindisten = Math.min(distence, mindisten);
            }
            Log.e("Sitename Service", sitename);
            handler.postDelayed(new Runnable(){
                @Override
                public void run() {
                    sysus("{SiteName:"+sitename+"}");
                    Log.d("tag","de sysus");
                }}, 500);
        }
    }

    public void StationSys(String type) {
        final Scheduler newThread = Schedulers.newThread();
        final Scheduler mainThread = AndroidSchedulers.mainThread();
        StationSubscriber subscriber = new StationSubscriber();
        StationApi.findReportByCity(type)
                .subscribeOn(newThread)
                .observeOn(mainThread)
                .subscribe(subscriber);
    }






    private class AirSubscriber extends Subscriber<AirReport> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            Log.e("onRrror",e.toString());
            handler.postDelayed(new Runnable(){
                @Override
                public void run() {
                    StationSys("County eq \'"+AirService.cityname+"\'");
                    //過兩秒後要做的事情
                    Log.d("tag","onError AirSubscriber");

                }}, 5000);

        }


        @Override
        public void onNext(AirReport report) {
            ArrayList<will.tw.airquality.air.model.Record> airreports = report.getResult().getRecords();
            String text;
            text=airreports.get(0).getSiteName();
            Log.e("countory Service",text);
            mAirReport = airreports;

            Bundle message = new Bundle();
            message.putInt("KeyOne", 1);
            Intent intent = new Intent("FilterString");
            intent.putExtras(message);
            sendBroadcast(intent);

            stopSelf();
        }
    }

    public void sysus(String type) {
        final Scheduler newThread = Schedulers.newThread();
        final Scheduler mainThread = AndroidSchedulers.mainThread();
        AirSubscriber subscriber = new AirSubscriber();
        AirApi.findReportByCity(type)
                .subscribeOn(newThread)
                .observeOn(mainThread)
                .subscribe(subscriber);
    }



}
