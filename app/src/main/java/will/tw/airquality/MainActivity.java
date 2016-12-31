package will.tw.airquality;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import will.tw.airquality.air.api.AirApi;
import will.tw.airquality.air.model.AirReport;
import will.tw.airquality.fragment.AirFragment;
import will.tw.airquality.location.location;
import will.tw.airquality.station.api.StationApi;
import will.tw.airquality.station.model.StationReport;


public class MainActivity extends AppCompatActivity {
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private location location;
    public static String sitename = "";
    private String cityname;
    private ProgressBar progressBar;
    private Handler handler = new Handler();


    public static ArrayList<AirReport> mAirReport;
    private static final int FRG_AIR_POS = 0;
    private static final int FRG_UV_POS = 1;
    private static final int FRG_WEATHER_POS = 2;
    private MainActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

    }

    public class MyTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
//            location = new location(activity);
//            cityname = location.testLocationProvider();
            Log.e("WOWOWOOWOWOWO", AirService.cityname);
            StationSys("County eq \'"+AirService.cityname+"\'");
            return null;
        }

        protected void onPreExecute(){
            // in main thread
        }


        protected void onProgressUpdate(Void... progress){
            // in main thread
        }

        protected void onPostExecute(Void result){
            // in main thread
        }

        protected void onCancelled(Void result){
            // in main thread
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        new MyTask().execute(null, null, null);

    }

    private void notifyFrgamentDataChanged() {
        Fragment robotfrg = mSectionsPagerAdapter.getActiveFragment(mViewPager, FRG_AIR_POS);
        if (robotfrg instanceof AirFragment) {
            AirFragment fr = (AirFragment) robotfrg;
            fr.updateData();
            Log.d("William", "RobotFragment.updateData()!");
        }
//
//        Fragment accountfrg = mSectionsPagerAdapter.getActiveFragment(mViewPager, FRG_ACCOUNT_POS);
//        if (accountfrg instanceof AccountFragment) {
//            AccountFragment fr = (AccountFragment) accountfrg;
//            fr.updateData();
//            Log.d(TAG, "AccountFragment.updateData()!");
//        }
    }

//    public void probar() {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                progressBar.setVisibility(View.INVISIBLE);
//            }
//        });
//    }


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
            handler.postDelayed(new Runnable(){
                @Override
                public void run() {
                    sysus("SiteName eq \'"+sitename+"\'");
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
            notifyFrgamentDataChanged();
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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }



    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case FRG_AIR_POS:
                    return AirFragment.newInstance(FRG_AIR_POS, "AirQuality");
//                case FRG_UV_POS:
//                    return RobotFragment.newInstance(FRG_ROBOT_POS, "Robot");\
//                case FRG_WEATHER_POS:
//                    return
                default:
                    return PlaceholderFragment.newInstance(position + 1);
            }


//            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }

        public Fragment getActiveFragment(ViewPager mViewPager, int frgAirPos) {
            String name = makeFragmentName(mViewPager.getId(), frgAirPos);
            return getSupportFragmentManager().findFragmentByTag(name);
        }
        private String makeFragmentName(int viewId, int index) {
            return "android:switcher:" + viewId + ":" + index;
        }
    }


    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }



}
