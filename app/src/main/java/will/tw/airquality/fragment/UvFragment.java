package will.tw.airquality.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import will.tw.airquality.AirService;
import will.tw.airquality.MainActivity;
import will.tw.airquality.R;
import will.tw.airquality.uv.model.Record;

/**
 * Created by william on 2017/1/4.
 */

public class UvFragment extends Fragment {

    private TextView text_sitename, text_uvi, text_publishagency, text_uvstatus, text_uvpublishtime;
    private double uvi;
    private ArrayList<Record> uvfrgreport = AirService.mUVReport;

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
        text_uvi = (TextView) view.findViewById(R.id.uvi);
        text_uvpublishtime = (TextView) view.findViewById(R.id.uvpublishtime);
        text_uvstatus = (TextView) view.findViewById(R.id.uvstatus);
        text_publishagency = (TextView) view.findViewById(R.id.PublishAgency);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateData();
    }

    public void updateData() {
        if (uvfrgreport != null) {
            text_sitename.setText(uvfrgreport.get(0).getCounty() + "," + uvfrgreport.get(0).getSiteName());
            text_uvi.setText(uvfrgreport.get(0).getUVI());
            if (uvfrgreport.get(0).getUVI().compareTo("") == 0){
                uvi =0;
                text_uvi.setText("0");

            }else{
                uvi = Double.valueOf(uvfrgreport.get(0).getUVI());
            }
            if (uvi <= 2) {
                text_uvi.setTextColor(getResources().getColor(R.color.green));
                text_uvstatus.setText(R.string.uv_low);
                text_uvstatus.setTextColor(getResources().getColor(R.color.green));

            } else if (uvi <= 5 && uvi >= 3) {
                text_uvi.setTextColor(getResources().getColor(R.color.goldyello));
                text_uvstatus.setText(R.string.uv_medium);
                text_uvstatus.setTextColor(getResources().getColor(R.color.goldyello));
            } else if (uvi >= 6 && uvi <= 7) {
                text_uvi.setTextColor(getResources().getColor(R.color.orange));
                text_uvstatus.setText(R.string.uv_high);
                text_uvstatus.setTextColor(getResources().getColor(R.color.orange));

            } else if (uvi >= 8 && uvi <= 10) {
                text_uvi.setTextColor(getResources().getColor(R.color.red));
                text_uvstatus.setText(R.string.uv_veryhigh);
                text_uvstatus.setTextColor(getResources().getColor(R.color.red));

            } else if (uvi >= 11) {
                text_uvi.setTextColor(getResources().getColor(R.color.perple));
                text_uvstatus.setText(R.string.uv_extreme);
                text_uvstatus.setTextColor(getResources().getColor(R.color.perple));

            }

            text_uvpublishtime.setText(uvfrgreport.get(0).getPublishTime());
            text_publishagency.setText(uvfrgreport.get(0).getPublishAgency());

        }
    }


}
