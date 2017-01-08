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

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import will.tw.airquality.air.model.Record;
import will.tw.airquality.gms.MessageEvent;
import will.tw.airquality.gms.location;


/**
 * Created by Ashbar on 2016/12/31.
 */

public class SplashActivity extends AppCompatActivity {


    private location gmslocation;
    private Double acevlaat, acevlon;
    private String acevcity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO 自动生成的方法存根
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        buildGoogleApiClient();
        EventBus.getDefault().register(this);
        gmslocation = new location();
        gmslocation.init(this);
        gmslocation.buildGoogleApiClient();
    }
//
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void helloEventBus(MessageEvent message){
        Log.e("wvwnBus",message.evenaddress+"."+message.evenlat+"."+message.evenlon);
        acevcity = message.evenaddress;
        acevlaat = message.evenlat;
        acevlon = message.evenlon;
        Intent intent = new Intent(SplashActivity.this, AirService.class);
//            Log.e("CallBack", mCityName);
        intent.putExtra("city", acevcity );
        intent.putExtra("lat", acevlaat);
        intent.putExtra("lon", acevlon);
        startService(intent);
    }

    @Subscribe(threadMode = ThreadMode.BackgroundThread)
    public void startEventBus(AirService.ActivityEvent activityEvent) {
        if (activityEvent.intent.compareTo("Start")==0){
            Intent i = new Intent(SplashActivity.this, MainActivity.class);
            //通过Intent打开最终真正的主界面Main这个Activity
            i.putExtra("donecity", acevcity);
            i.putExtra("donelat", acevlaat);
            i.putExtra("donelon", acevlon);
            SplashActivity.this.startActivity(i);    //启动Main界面
            SplashActivity.this.finish();    //关闭自己这个开场屏
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        gmslocation.disconnect();
//        EventBus.getDefault().unregister(this);
        Log.e("William HandlerThread", "Destroy");
    }

    @Override
    protected void onStart() {
        super.onStart();
        gmslocation.connect();
    }

}
