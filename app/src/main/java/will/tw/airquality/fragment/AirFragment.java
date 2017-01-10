package will.tw.airquality.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import will.tw.airquality.AirService;
import will.tw.airquality.R;

/**
 * Created by william on 2016/12/30.
 */

public class AirFragment extends Fragment {
    private String majorpollutant;

    private TextView text_sitename, text_psi, text_majorpollutant, text_status, text_publishtime;
    private ImageButton imageButton;
    private int psi;


    public static AirFragment newInstance(int sectionNumber, String title) {
        AirFragment fragment = new AirFragment();
        Bundle args = new Bundle();

//        fragment.setArguments(args);
        return fragment;
    }

    public AirFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.air_layout, container, false);
    }




    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        text_sitename = (TextView) view.findViewById(R.id.sitename);
        text_psi = (TextView) view.findViewById(R.id.psi);
        text_majorpollutant = (TextView) view.findViewById(R.id.majorpollutant);
        text_status = (TextView) view.findViewById(R.id.status);
        text_publishtime = (TextView) view.findViewById(R.id.publishtime);
        imageButton = (ImageButton) view.findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), AirDetal.class);
                startActivity(intent);

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        updateData();
    }


    public void updateData() {
        if (AirService.mAirReport != null) {
            text_sitename.setText(AirService.mAirReport.get(0).getCounty() + "," + AirService.mAirReport.get(0).getSiteName());
            psi = Integer.valueOf(AirService.mAirReport.get(0).getPSI());
            if (psi <= 50) {
                text_psi.setTextColor(getResources().getColor(R.color.green));
                text_status.setTextColor(getResources().getColor(R.color.green));
            } else if (psi >= 51 && psi <= 100) {
                text_psi.setTextColor(getResources().getColor(R.color.goldyello));
                text_status.setTextColor(getResources().getColor(R.color.goldyello));
            }else if (psi >=101 && psi <=150){
                text_psi.setTextColor(getResources().getColor(R.color.orange));
                text_status.setTextColor(getResources().getColor(R.color.orange));
            } else if(psi >=151 && psi <=200){
                text_psi.setTextColor(getResources().getColor(R.color.red));
                text_status.setTextColor(getResources().getColor(R.color.red));
            }else if (psi >= 201 &&psi <=300){
                text_psi.setTextColor(getResources().getColor(R.color.perple));
                text_status.setTextColor(getResources().getColor(R.color.perple));
            } else {
                text_psi.setTextColor(getResources().getColor(R.color.deepred));
                text_status.setTextColor(getResources().getColor(R.color.deepred));
            }
            text_psi.setText(AirService.mAirReport.get(0).getPSI());


            majorpollutant = AirService.mAirReport.get(0).getMajorPollutant();
            if (majorpollutant.compareTo("") == 0) {
                text_majorpollutant.setText("無空氣汙染指標物");
            } else {
                text_majorpollutant.setText(AirService.mAirReport.get(0).getMajorPollutant());
            }
            text_status.setText(AirService.mAirReport.get(0).getStatus());
            text_publishtime.setText(AirService.mAirReport.get(0).getPublishTime());

        }
    }


}
