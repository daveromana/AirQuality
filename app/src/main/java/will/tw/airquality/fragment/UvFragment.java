package will.tw.airquality.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import will.tw.airquality.MainActivity;
import will.tw.airquality.R;

/**
 * Created by william on 2017/1/4.
 */

public class UvFragment extends Fragment {

    private TextView text_sitename, text_country, text_psi, text_majorpollutant, text_status, text_so2, text_co, text_o3, text_pm10,
            text_pm25, text_no2, text_windspeed, text_winddirec, text_fpmi, text_nox, text_no, text_publishtime;


    public static UvFragment newInstance(int sectionNumber, String title) {
        UvFragment fragment = new UvFragment();
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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        text_sitename = (TextView) view.findViewById(R.id.uvsitename);


    }

    @Override
    public void onResume() {
        super.onResume();
        updateData();
    }

    public void updateData() {
        if (MainActivity.mUVReport != null) {
            text_sitename.setText(MainActivity.mUVReport.get(0).getCounty()+","+MainActivity.mUVReport.get(0).getSiteName());


        }
    }


}
