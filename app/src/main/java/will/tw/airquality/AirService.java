package will.tw.airquality;


import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;


import java.util.ArrayList;


import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import will.tw.airquality.air.api.AirApi;
import will.tw.airquality.air.model.AirReport;
import will.tw.airquality.air.model.Record;
import will.tw.airquality.station.api.StationApi;
import will.tw.airquality.station.model.StationReport;

/**
 * Created by Ashbar on 2016/12/31.
 */

public class AirService extends IntentService {
    public static String cityname;

    public static String sitename = "";
    private Handler handler = new Handler();
    public static ArrayList<Record> mAirReport;
    private ArrayList<will.tw.airquality.station.model.Record> stationreports;


    public AirService() {
        super("Retrofit");

    }


    @Override
    protected void onHandleIntent(Intent intent) {
        StationSys("{County:" + SplashActivity.Addresscode + "}");
    }

    //
    @Override
    public void onCreate() {
        super.onCreate();
//        cityname = testLocationProvider();
//        Log.e("William service", cityname);
//        StationSys("{County:"+cityname+"}");
    }

    /**
     * Called when the activity is first created.
     */


    private class StationSubscriber extends Subscriber<StationReport> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            Log.e("onRrror", e.toString());
            onCreate();
        }

        @Override
        public void onNext(StationReport report) {
            stationreports = report.getResult().getRecords();
            Double lat;
            Double lon;
            Double nowlat = SplashActivity.lat;
            Double nowlon = SplashActivity.lon;
            Double mindisten = 99999999999999.9;
            for (int i = 0; i < stationreports.size(); i++) {
                lat = Double.valueOf(stationreports.get(i).getTWD97Lat());
                lon = Double.valueOf(stationreports.get(i).getTWD97Lon());
                Double sum = (nowlat - lat) * (nowlat - lat) + (nowlon - lon) * (nowlon - lon);
                Double distence = Math.sqrt(sum);
                if (distence < mindisten) {
                    sitename = stationreports.get(i).getSiteName();
                    mindisten = distence;
                }
//                mindisten = Math.min(distence, mindisten);
            }
            Log.e("Sitename Service", sitename);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    sysus("{SiteName:" + sitename + "}");
                    Log.d("tag", "de sysus");
                }
            }, 500);
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
            Log.e("onRrror", e.toString());
            Log.e("onErroor", "AirSubscriber Error");

        }


        @Override
        public void onNext(AirReport airReport) {
            ArrayList<Record> airreports = airReport.getResult().getRecords();
            String text;
            text = airreports.get(0).getSiteName();
            Log.e("countory Service", text);
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
