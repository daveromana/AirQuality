package will.tw.airquality.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import will.tw.airquality.R;

/**
 * Created by william on 2017/1/10.
 */

public class WeatherFragment extends Fragment {
    public static Fragment newInstance(int frgWeatherPos, String weather) {

        WeatherFragment fragment = new WeatherFragment();
        Bundle args = new Bundle();

//        fragment.setArguments(args);
        return fragment;
    }

    public WeatherFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.air_layout, container, false);
    }
    public void updateData() {

    }
}
