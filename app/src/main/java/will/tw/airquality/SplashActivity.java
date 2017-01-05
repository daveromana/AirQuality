package will.tw.airquality;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.List;
import java.util.Locale;


/**
 * Created by Ashbar on 2016/12/31.
 */

public class SplashActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {


    public static Double lon, lat;
    public static String Addresscode;
    protected GoogleApiClient mGoogleApiClient;
    protected Location mLastLocation;
    public List<Address> lstAddress;
    public static final String TAG = "There";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO 自动生成的方法存根
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        buildGoogleApiClient();

//        new MyTask().execute(null, null, null);

    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        Log.e("William HandlerThread", "Destroy");
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    public class MyTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            Intent intent = new Intent(SplashActivity.this, AirService.class);
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
            if (strValue == "1") {
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                //通过Intent打开最终真正的主界面Main这个Activity
                SplashActivity.this.startActivity(i);    //启动Main界面
                SplashActivity.this.finish();    //关闭自己这个开场屏
            }
        }
    };


    public void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        // 這行指令在 IDE 會出現紅線，不過仍可正常執行，可不予理會
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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

            if (mLastLocation != null) {
                lat = mLastLocation.getLatitude();
                lon = mLastLocation.getLongitude();
                Log.e("google service :", lat + ":" + lon);
                Geocoder gc = new Geocoder(this, Locale.getDefault());    //Ša°Ï:¥xÆW
                lstAddress = gc.getFromLocation(lat, lon, 1);
                Addresscode = lstAddress.get(0).getAdminArea();
                Log.e("google Geocode :", Addresscode);
                if (Addresscode.compareTo("台北市") == 0) {
                    Addresscode = "臺北市";
                } else if (Addresscode.compareTo("台東市") == 0) {
                    Addresscode = "臺東市";
                } else if (Addresscode.compareTo("台東縣") == 0) {
                    Addresscode = "臺東縣";
                } else if (Addresscode.compareTo("台南市") == 0) {
                    Addresscode = "臺南市";
                } else if (Addresscode.compareTo("台中市") == 0) {
                    Addresscode = "臺中市";
                }else{
                    Addresscode = lstAddress.get(0).getAdminArea();
                }
            } else {
                Toast.makeText(this, "偵測不到定位，請確認定位功能已開啟。", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        new MyTask().execute(null, null, null);
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
