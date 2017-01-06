package will.tw.airquality;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

import will.tw.airquality.air.model.Record;
import will.tw.airquality.gms.location;


/**
 * Created by Ashbar on 2016/12/31.
 */

public class SplashActivity extends AppCompatActivity implements location.MyLocationCallBack {


    private Double mlon, mlat;
    private location gmslocation;
    private String mCityName;
    private Serializable Airreport;
    private ArrayList donereport;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO 自动生成的方法存根
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        buildGoogleApiClient();

        gmslocation = new location();
        gmslocation.init(this);
        gmslocation.buildGoogleApiClient();
        gmslocation.addLinstner(this);

    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
//        gmslocation.disconnect();
        Log.e("William HandlerThread", "Destroy");
    }

    @Override
    protected void onStart() {
        super.onStart();
        gmslocation.connect();
    }

    @Override
    public void done(String CityName,Double Lat, Double Lon) {
        mlon = Lon;
        mlat = Lat;
        mCityName = CityName;
        new MyTask().execute(null, null, null);

    }


    public class MyTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            Intent intent = new Intent(SplashActivity.this, AirService.class);
            Log.e("CallBack", mCityName);
            intent.putExtra("city", mCityName);
            intent.putExtra("lat", mlat);
            intent.putExtra("lon", mlon);
            startService(intent);
            final String Action = "FilterString";
            IntentFilter filter = new IntentFilter(Action);
            // 將 BroadcastReceiver 在 Activity 掛起來。
            registerReceiver(receiver, filter);
            return null;
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


    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 處理 Service 傳來的訊息。
            Bundle message = intent.getExtras();
            int value = message.getInt("KeyOne");
            String strValue = String.valueOf(value);

            ArrayList arrayList = message.getIntegerArrayList("list");
            ArrayList list2 = (ArrayList<Record>) arrayList.get(0);
            Log.e("William", list2.ge);
            if (Objects.equals(strValue, "1")) {
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                //通过Intent打开最终真正的主界面Main这个Activity
                i.putExtra("donecity", mCityName);
                i.putExtra("donelat", mlat);
                i.putExtra("donelon", mlon);
                SplashActivity.this.startActivity(i);    //启动Main界面
                SplashActivity.this.finish();    //关闭自己这个开场屏
            }
        }
    };


}
