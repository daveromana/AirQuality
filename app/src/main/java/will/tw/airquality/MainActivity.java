package will.tw.airquality;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import will.tw.airquality.air.api.AirApi;
import will.tw.airquality.air.model.AirReport;
import will.tw.airquality.location.location;
import will.tw.airquality.station.api.StationApi;
import will.tw.airquality.station.model.StationReport;


public class MainActivity extends AppCompatActivity {

    private location location;
    public static String sitename = "";

    private String cityname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        location = new location(this);
        cityname = location.testLocationProvider();
        Log.e("WOWOWOOWOWOWO", cityname);
        StationSys("County eq \'"+cityname+"\'");

    }




    private class StationSubscriber extends Subscriber<ArrayList<StationReport>> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            Log.e("onRrror",e.toString());

        }

        @Override
        public void onNext(ArrayList<StationReport> report) {
            Double lat;
            Double lon;
            Double nowlat = location.mLatitude;
            Double nowlon = location.mLongitude;
            Double mindisten = 99999999999999.9;
            for (int i= 0; i<report.size(); i ++){
                lat = Double.valueOf(report.get(i).getTWD97Lat());
                lon = Double.valueOf(report.get(i).getTWD97Lon());
                Double sum = (nowlat - lat)*(nowlat - lat)+(nowlon - lon)*(nowlon - lon);
                Double distence = Math.sqrt(sum);
                if (distence<mindisten){
                    sitename = report.get(i).getSiteName();
                    mindisten = distence;
                }
//                mindisten = Math.min(distence, mindisten);
            }
            Log.e("Sitename", sitename);
            sysus("SiteName eq \'"+sitename+"\'");
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

    private class AirSubscriber extends Subscriber<ArrayList<AirReport>> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            Log.e("onRrror",e.toString());

        }

        @Override
        public void onNext(ArrayList<AirReport> report) {
            String text;
            text=report.get(0).getSiteName();
            Log.e("countory",text);

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
