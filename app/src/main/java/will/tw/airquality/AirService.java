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
import will.tw.airquality.acculocation.api.AcculocaiotnApi;
import will.tw.airquality.acculocation.model.AcculocationReport;
import will.tw.airquality.accuweather.api.AccuWeatherApi;
import will.tw.airquality.accuweather.model.AccuweatherReport;
import will.tw.airquality.air.api.AirApi;
import will.tw.airquality.air.model.AirReport;
import will.tw.airquality.air.model.Record;
import will.tw.airquality.gms.MessageEvent;
import will.tw.airquality.station.api.StationApi;
import will.tw.airquality.station.model.StationReport;
import will.tw.airquality.uv.api.UvApi;
import will.tw.airquality.uv.model.UvReport;

/**
 * Created by Ashbar on 2016/12/31.
 */

public class AirService extends IntentService {
    public static ArrayList<will.tw.airquality.uv.model.Record> mUVReport;

    private String acculocatiobkey;
    private String accuweather;
    private String sitename;
    private Handler handler = new Handler();
    public static ArrayList<Record> mAirReport;
    private String servicecity;
    private Double servicelon, servicelat;
    private ArrayList<will.tw.airquality.station.model.Record> stationreports;
    private ArrayList<Record> posairreport;
    private String uvsitename;

    public AirService() {
        super("Retrofit");

    }


    @Override
    protected void onHandleIntent(Intent intent) {
        servicecity = intent.getStringExtra("city");
        servicelat = intent.getDoubleExtra("lat", 0);
        servicelon = intent.getDoubleExtra("lon", 0);
//        StationSys("{County:" + servicecity + "}");
        sub();

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

    private void sub() {
//        servicecity = intent.getStringExtra("city");
//        servicelat = intent.getDoubleExtra("lat", 0);
//        servicelon = intent.getDoubleExtra("lon", 0);
        StationSys("{County:" + servicecity + "}");
        uvsysus("{County:" + servicecity + "}");
        acculocationsys(servicelat+","+servicelon);


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
            sub();
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


    private class UVSubscriber extends Subscriber<UvReport> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            Log.e("onRrror", e.toString());
            Log.e("onErroor", "UvSubscriber Error");
        }


        @Override
        public void onNext(UvReport uvReport) {
            ArrayList<will.tw.airquality.uv.model.Record> uvreports = uvReport.getResult().getRecords();
            String strlat;
            String strlon;
            Double lat;
            Double lon;
            Double nowlat = servicelat;
            Double nowlon = servicelon;
            Double mindisten = 99999999999999.9;
            for (int i = 0; i < uvreports.size(); i++) {
                strlat = uvreports.get(i).getWGS84Lat().replace(",", "").replace(".", "");
                strlon = uvreports.get(i).getWGS84Lon().replace(",", "").replace(".", "");
                lat = Double.valueOf(strlat.substring(0, 2) + "." + strlat.substring(2, strlat.length()));
                lon = Double.valueOf(strlon.substring(0, 3) + "." + strlon.substring(3, strlon.length()));
                Double sum = (nowlat - lat) * (nowlat - lat) + (nowlon - lon) * (nowlon - lon);
                Double distence = Math.sqrt(sum);
                if (distence < mindisten) {
                    uvsitename = uvreports.get(i).getSiteName();
                    mindisten = distence;
                }
                mindisten = Math.min(distence, mindisten);
            }
            Log.e("William UV", uvsitename);

            uvsitenamesysus("{SiteName:" + uvsitename + "}");


        }
    }

    public void uvsysus(String type) {
        final Scheduler newThread = Schedulers.newThread();
        final Scheduler mainThread = AndroidSchedulers.mainThread();
        UVSubscriber subscriber = new UVSubscriber();
        UvApi.findReportByCity(type)
                .subscribeOn(newThread)
                .observeOn(mainThread)
                .subscribe(subscriber);
    }

    private class UVSiteSubscriber extends Subscriber<UvReport> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            Log.e("onRrror", e.toString());
            Log.e("onErroor", "UvStationSubscriber Error");
        }


        @Override
        public void onNext(UvReport uvReport) {
            ArrayList<will.tw.airquality.uv.model.Record> uvstationreports = uvReport.getResult().getRecords();
            mUVReport = uvstationreports;
            Log.e("UVSiteName Finish", mUVReport.get(0).getSiteName());
        }
    }

    public void uvsitenamesysus(String type) {
        final Scheduler newThread = Schedulers.newThread();
        final Scheduler mainThread = AndroidSchedulers.mainThread();
        UVSiteSubscriber subscriber = new UVSiteSubscriber();
        UvApi.findReportByCity(type)
                .subscribeOn(newThread)
                .observeOn(mainThread)
                .subscribe(subscriber);
    }






    private class AcculocaiotnSubscriber extends Subscriber<AcculocationReport> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            Log.e("onRrror", e.toString());
            Log.e("onErroor", "AcculocaiotnSubscriber Error");
        }


        @Override
        public void onNext(AcculocationReport acculocationReport) {
            acculocatiobkey = acculocationReport.getKey();
            Log.e("AcculocaiotnSubscriber", acculocatiobkey);
            accuweathersys(acculocatiobkey);
        }
    }

    public void acculocationsys(String type) {
        final Scheduler newThread = Schedulers.newThread();
        final Scheduler mainThread = AndroidSchedulers.mainThread();
        AcculocaiotnSubscriber subscriber = new AcculocaiotnSubscriber();
        AcculocaiotnApi.findReportByCity(type)
                .subscribeOn(newThread)
                .observeOn(mainThread)
                .subscribe(subscriber);
    }



    private class AccuWeatherSubscriber extends Subscriber<AccuweatherReport> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            Log.e("onRrror", e.toString());
            Log.e("onErroor", "AcculocaiotnSubscriber Error");
        }


        @Override
        public void onNext(AccuweatherReport accuweatherReport) {
            accuweather = accuweatherReport.getHeadline().getText();
            Log.e("AccuWeather", accuweather);
        }
    }

    public void accuweathersys(String type) {
        final Scheduler newThread = Schedulers.newThread();
        final Scheduler mainThread = AndroidSchedulers.mainThread();
        AccuWeatherSubscriber subscriber = new AccuWeatherSubscriber();
        AccuWeatherApi.findReportByLocaiotnKey(type)
                .subscribeOn(newThread)
                .observeOn(mainThread)
                .subscribe(subscriber);
    }


}
