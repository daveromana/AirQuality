package will.tw.airquality.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import will.tw.airquality.R;

/**
 * Created by william on 2017/1/4.
 */

public class UvFragment extends Fragment {


    public static AirFragment newInstance(int sectionNumber, String title) {
        AirFragment fragment = new AirFragment();
        Bundle args = new Bundle();
//        fragment.setArguments(args);
        return fragment;
    }

    public UvFragment() {
    }

    //    @Override
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.uv_layout, container, false);
    }

    public void updateData() {
    }


}
