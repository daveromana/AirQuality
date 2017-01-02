package will.tw.airquality;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;



/**
 * Created by Ashbar on 2016/12/31.
 */

public class SplashActivity extends AppCompatActivity {




    private Handler mHandler;
    public static final String TAG = "There";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO 自动生成的方法存根
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }
    Runnable mBackgroundRunnable = new Runnable() {
        @Override
        public void run() {
            // ----------模拟耗时的操作，开始---------------
            while (AirService.mAirReport == null) {
                Log.e(TAG, "thread running!");
                try {
                    Thread.sleep(200);
                    Intent intent = new Intent(SplashActivity.this, AirService.class);
                    startService(intent);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Intent i = new Intent(SplashActivity.this, MainActivity.class);
            //通过Intent打开最终真正的主界面Main这个Activity
            SplashActivity.this.startActivity(i);    //启动Main界面
            SplashActivity.this.finish();    //关闭自己这个开场屏
            // ----------模拟耗时的操作，结束---------------
        }
    };


    @Override
    protected void onResume() {
        super.onResume();
        new MyTask().execute(null, null, null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mBackgroundRunnable);
        Log.e("William HandlerThread", "Destroy");
    }

    public class MyTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            HandlerThread thread = new HandlerThread("MyHandlerThread");
            thread.start();// 创建一个HandlerThread并启动它
            mHandler = new Handler(thread.getLooper());// 使用HandlerThread的looper对象创建Handler，如果使用默认的构造方法，很有可能阻塞UI线程
            mHandler.post(mBackgroundRunnable);// 将线程post到Handler中
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
}
