package will.tw.airquality.fragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


import java.util.ArrayList;

import will.tw.airquality.AirService;
import will.tw.airquality.R;
import will.tw.airquality.air.model.Record;

/**
 * Created by Ashbar on 2017/1/10.
 */

public class AirDetal extends AppCompatActivity {
    private TextView text_psi, text_sitename, text_county, text_majorpollutant, text_status, text_so2, text_co, text_o3, text_pm10, text_pm25, text_no2, text_windspeed, text_winddirec, text_fpm, text_nox, text_no, text_publishtime;
    private ArrayList<Record> detalrepo = AirService.mAirReport;
    private String majorpollutant;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.airdetal_layout);
        text_sitename = (TextView) findViewById(R.id.airsitename);
        text_county = (TextView) findViewById(R.id.aircounty);
        text_psi = (TextView) findViewById(R.id.airpsi);
        text_status = (TextView) findViewById(R.id.airstatus);
        text_majorpollutant = (TextView) findViewById(R.id.airmajorpollutant);
        text_so2 = (TextView) findViewById(R.id.so2);
        text_co = (TextView) findViewById(R.id.co);
        text_o3 = (TextView) findViewById(R.id.o3);
        text_pm10 = (TextView) findViewById(R.id.pm10);
        text_pm25 = (TextView) findViewById(R.id.pm25);
        text_no2 = (TextView) findViewById(R.id.no2);
        text_windspeed = (TextView) findViewById(R.id.windspeed);
        text_winddirec = (TextView) findViewById(R.id.winddirec);
        text_fpm = (TextView) findViewById(R.id.fpmi);
        text_nox = (TextView) findViewById(R.id.nox);
        text_no = (TextView) findViewById(R.id.no);
        text_publishtime = (TextView) findViewById(R.id.airpublishtime);
        update();

    }

    private void update() {
        text_sitename.setText(detalrepo.get(0).getSiteName());
        text_county.setText(detalrepo.get(0).getCounty());
        text_psi.setText(detalrepo.get(0).getPSI());
        majorpollutant = detalrepo.get(0).getMajorPollutant();
        if (majorpollutant.compareTo("") == 0) {
            text_majorpollutant.setText("無空氣汙染指標物");
        } else {
            text_majorpollutant.setText(detalrepo.get(0).getMajorPollutant());
        }
        text_status.setText(detalrepo.get(0).getStatus());
        text_so2.setText(detalrepo.get(0).getSO2() + " ppb");
        text_co.setText(detalrepo.get(0).getCO()+" ppm");
        text_o3.setText(detalrepo.get(0).getO3()+" ppb");
        text_pm10.setText(detalrepo.get(0).getPM10()+" μg/m3");
        text_pm25.setText(detalrepo.get(0).getPM25()+" μg/m3");
        text_no2.setText(detalrepo.get(0).getNO2() + "ppb");
        text_windspeed.setText(detalrepo.get(0).getWindSpeed() + " m/sec");
        text_winddirec.setText(detalrepo.get(0).getWindDirec() + " degrees");
        text_fpm.setText(detalrepo.get(0).getFPMI());
        text_nox.setText(detalrepo.get(0).getNOx() + " ppb");
        text_no.setText(detalrepo.get(0).getNO() + " ppb");
        text_publishtime.setText(detalrepo.get(0).getPublishTime());
    }
}
