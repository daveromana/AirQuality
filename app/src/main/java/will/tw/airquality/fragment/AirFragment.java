package will.tw.airquality.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import will.tw.airquality.AirService;
import will.tw.airquality.R;

/**
 * Created by william on 2016/12/30.
 */

public class AirFragment extends Fragment {
    private String majorpollutant;

    private TextView text_sitename, text_country, text_psi, text_majorpollutant, text_status, text_so2, text_co, text_o3, text_pm10,
            text_pm25, text_no2, text_windspeed, text_winddirec, text_fpmi, text_nox, text_no, text_publishtime;
    private ProgressBar progressBar;

    public static AirFragment newInstance(int sectionNumber, String title) {
        AirFragment fragment = new AirFragment();
        Bundle args = new Bundle();
//        fragment.setArguments(args);
        return fragment;
    }

    public AirFragment() {
    }

    //    @Override
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.air_layout, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        text_sitename = (TextView) view.findViewById(R.id.sitename);
        text_country = (TextView) view.findViewById(R.id.county);
        text_psi = (TextView) view.findViewById(R.id.psi);
        text_majorpollutant = (TextView) view.findViewById(R.id.majorpollutant);
        text_status = (TextView) view.findViewById(R.id.status);
        text_so2 = (TextView) view.findViewById(R.id.so2);
        text_co = (TextView) view.findViewById(R.id.co);
        text_o3 = (TextView) view.findViewById(R.id.o3);
        text_pm10 = (TextView) view.findViewById(R.id.pm10);
        text_pm25 = (TextView) view.findViewById(R.id.pm25);
        text_no2 = (TextView) view.findViewById(R.id.no2);
        text_windspeed = (TextView) view.findViewById(R.id.windspeed);
        text_winddirec = (TextView) view.findViewById(R.id.winddirec);
        text_fpmi = (TextView) view.findViewById(R.id.fpmi);
        text_no = (TextView) view.findViewById(R.id.no);
        text_nox = (TextView) view.findViewById(R.id.nox);
        text_publishtime = (TextView) view.findViewById(R.id.publishtime);
//        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);


    }
    //    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
//        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
////        textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
//        return rootView;
//    }


    @Override
    public void onResume() {
        super.onResume();
        updateData();
    }

    public void updateData() {
        if (AirService.mAirReport != null) {
            text_sitename.setText(AirService.mAirReport.get(0).getSiteName());
            text_country.setText(AirService.mAirReport.get(0).getCounty());
            text_psi.setText(AirService.mAirReport.get(0).getPSI());
            majorpollutant = AirService.mAirReport.get(0).getMajorPollutant();
            if(majorpollutant.compareTo("") == 0) {
                text_majorpollutant.setText("無空氣汙染指標物");
            } else {
                text_majorpollutant.setText(AirService.mAirReport.get(0).getMajorPollutant());
            }
            text_status.setText(AirService.mAirReport.get(0).getStatus());
            text_so2.setText(AirService.mAirReport.get(0).getSO2());
            text_co.setText(AirService.mAirReport.get(0).getCO());
            text_o3.setText(AirService.mAirReport.get(0).getO3());
            text_pm10.setText(AirService.mAirReport.get(0).getPM10());
            text_pm25.setText(AirService.mAirReport.get(0).getPM25());
            text_no2.setText(AirService.mAirReport.get(0).getNO2());
            text_windspeed.setText(AirService.mAirReport.get(0).getWindSpeed());
            text_winddirec.setText(AirService.mAirReport.get(0).getWindDirec());
            text_fpmi.setText(AirService.mAirReport.get(0).getFPMI());
            text_no.setText(AirService.mAirReport.get(0).getNO());
            text_nox.setText(AirService.mAirReport.get(0).getNOx());
            text_publishtime.setText(AirService.mAirReport.get(0).getPublishTime());
            Log.e("AirFragment", AirService.mAirReport.get(0).getCounty());

        }
    }
}
