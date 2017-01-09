package will.tw.airquality;


import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;


import java.util.ArrayList;


import de.greenrobot.event.EventBus;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import will.tw.airquality.air.api.AirApi;
import will.tw.airquality.air.model.AirReport;
import will.tw.airquality.air.model.Record;
import will.tw.airquality.gms.MessageEvent;
import will.tw.airquality.station.api.StationApi;
import will.tw.airquality.station.model.StationReport;

/**
 * Created by Ashbar on 2016/12/31.
 */

public class AirService extends IntentService {

    private String sitename;
    private Handler handler = new Handler();
    public static ArrayList<Record> mAirReport;
    private String servicecity;
    private Double servicelon, servicelat;
    private ArrayList<will.tw.airquality.station.model.Record> stationreports;
    private ArrayList<Record> posairreport;


    public AirService() {
        super("Retrofit");

    }


    @Override
    protected void onHandleIntent(Intent intent) {
        servicecity = intent.getStringExtra("city");
        servicelat = intent.getDoubleExtra("lat", 0);
        servicelon = intent.getDoubleExtra("lon", 0);
        StationSys("{County:" + servicecity + "}");
    }


    /**
     * Called when the activity is first created.
     */
    class MyServerThread extends Thread {
        @Override
        public void run() {
            EventBus.getDefault().post(new ActivityEvent("Start", posairreport));

        }
    }


    public class ActivityEvent {

        public String intent;
        public ArrayList<Record> Record;

        public ActivityEvent(String message, ArrayList<Record> pore) {
            this.intent = message;
            this.Record = pore;
        }
    }



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
            Double nowlat = servicelat;
            Double nowlon = servicelon;
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
            posairreport = airreports;
            new MyServerThread().start();
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
