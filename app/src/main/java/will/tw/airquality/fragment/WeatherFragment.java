package will.tw.airquality.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import will.tw.airquality.AirService;
import will.tw.airquality.R;
import will.tw.airquality.air.model.AirReport;

import static android.hardware.Sensor.TYPE_AMBIENT_TEMPERATURE;

/**
 * Created by william on 2017/1/10.
 */

public class WeatherFragment extends Fragment {
    private TextView acculocation,temper;
    private ImageButton imageButton;

    public static Fragment newInstance(int frgWeatherPos, String weather) {

        WeatherFragment fragment = new WeatherFragment();
        Bundle args = new Bundle();

//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateData();
    }

    public WeatherFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.weather_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        acculocation = (TextView)view.findViewById(R.id.acculocation);
        temper = (TextView) view.findViewById(R.id.temper);
//        imageButton = (ImageButton) view.findViewById(R.id.imageButton2);

    }

    public void updateData() {
        acculocation.setText(AirService.accucity + "," + AirService.accuarea);
        temper.setText(AirService.mHourTemperature.replace(".", "").substring(0, 2) + "°C");
//        Picasso.with(Context context)
//                .load("http://vignette3.wikia.nocookie.net/logopedia/images/a/a9/AccuWeather.svg/revision/latest?cb=20130419211253")
//                .into(imageButton);
    }
}
