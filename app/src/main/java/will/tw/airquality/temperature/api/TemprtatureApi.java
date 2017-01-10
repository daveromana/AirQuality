package will.tw.airquality.temperature.api;

/**
 * Created by Ashbar on 2017/1/11.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import rx.Observable;
import will.tw.airquality.BuildConfig;
import will.tw.airquality.temperature.model.TemperatureReport;

public class TemprtatureApi {
    private static final String BASE_URL = "http://dataservice.accuweather.com/";
    private static final String API = "apikey";
    private static final String KEY_API = "YiZjNe17sw324z3fjg7dc3zMOKJDSxWL";
    private static final String LANGUAGE ="language";
    private static final String KEY_LANGUAGE = "zh-tw";
    private static final String METRIC = "metric";
    private static final String KEY_METRIC = "true";

    private static UvService sService;

    private static synchronized UvService getService() {
        if (sService == null) {
            final GsonConverterFactory converterFactory = GsonConverterFactory.create();
            final RxJavaCallAdapterFactory callAdapterFactory = RxJavaCallAdapterFactory.create();

            OkHttpClient httpClient = new OkHttpClient();
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                httpClient = new OkHttpClient.Builder().addInterceptor(logging).build();
            }

            final Retrofit retrofit =
                    new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .client(httpClient)
                            .addConverterFactory(converterFactory)
                            .addCallAdapterFactory(callAdapterFactory)
                            .build();
            sService = retrofit.create(UvService.class);
        }
        return sService;
    }

    public static Observable<ArrayList<TemperatureReport>> findReportByLocaiotnKey(String type) {
        final Map<String, String> parameters = new HashMap<>();

        parameters.put(LANGUAGE,KEY_LANGUAGE);
        parameters.put(METRIC,KEY_METRIC);
        parameters.put(API,KEY_API);

        return getService().findReport(type ,parameters);
    }

    private interface UvService {
        @GET("forecasts/v1/hourly/1hour/{location}")
        Observable<ArrayList<TemperatureReport>> findReport(@Path("location") String locaiotn, @QueryMap Map<String, String> parameters );
    }
}
