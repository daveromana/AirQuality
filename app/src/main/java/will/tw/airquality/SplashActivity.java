package will.tw.airquality;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;

import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import will.tw.airquality.air.api.AirApi;
import will.tw.airquality.air.model.AirReport;
import will.tw.airquality.station.api.StationApi;
import will.tw.airquality.station.model.StationReport;


/**
 * Created by Ashbar on 2016/12/31.
 */

public class SplashActivity extends AppCompatActivity {


    public static String sitename = "";
    private String station;
    private Handler handler = new Handler();

    public static ArrayList<AirReport> mAirReport;
    //    private AirService airService = null;
    private String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO 自动生成的方法存根
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Intent intent = new Intent(SplashActivity.this, AirService.class);
        startService(intent);





//        StationSys("County eq \'"+AirService.cityname+"\'");

        new Handler().postDelayed(new Runnable() {
            // 为了减少代码使用匿名Handler创建一个延时的调用
            public void run() {
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                //通过Intent打开最终真正的主界面Main这个Activity
                SplashActivity.this.startActivity(i);    //启动Main界面
                SplashActivity.this.finish();    //关闭自己这个开场屏

            }
        }, 3000);   //5秒
//        StationSys("County eq \'"+AirService.cityname+"\'");

    }

    @Override
    protected void onResume() {
        super.onResume();
//        new MyTask().execute(null, null, null);
    }

    public class MyTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
//            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
//            startActivity(intent);


            StationSys("County eq \'"+AirService.cityname+"\'");
            return null;
        }

        protected void onPreExecute() {
            // in main thread
        }


        protected void onProgressUpdate(Void... progress) {
            // in main thread
        }

        protected void onPostExecute(Void result) {
            // in main thread

        }

        protected void onCancelled(Void result) {
            // in main thread
        }

    }


    private class StationSubscriber extends Subscriber<ArrayList<StationReport>> {
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
                    Log.d("tag","onError StationSubscriber");

                }}, 5000);
        }

        @Override
        public void onNext(ArrayList<StationReport> report) {
            Double lat;
            Double lon;
            Double nowlat = AirService.mLatitude;
            Double nowlon = AirService.mLongitude;
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
//            handler.postDelayed(new Runnable(){
//                @Override
//                public void run() {
//                    sysus("SiteName eq \'"+sitename+"\'");
//                    Log.d("tag","de sysus");
//                }}, 500);
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
            handler.postDelayed(new Runnable(){
                @Override
                public void run() {
                    StationSys("County eq \'"+AirService.cityname+"\'");
                    //過兩秒後要做的事情
                    Log.d("tag","onError AirSubscriber");

                }}, 5000);

        }


        @Override
        public void onNext(ArrayList<AirReport> report) {
//            probar();
            String text;
            text=report.get(0).getSiteName();
            Log.e("countory",text);
            mAirReport = report;
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
