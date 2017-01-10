package will.tw.airquality.acculocation.api;

/**
 * Created by william on 2017/1/10.
 */
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;
import will.tw.airquality.BuildConfig;
import will.tw.airquality.acculocation.model.AcculocationReport;

public class AcculocaiotnApi {


    private static final String BASE_URL = "http://dataservice.accuweather.com/";
    private static final String API = "apikey";
    private static final String KEY_API = "YiZjNe17sw324z3fjg7dc3zMOKJDSxWL";
    private static final String Q ="q";
    private static final String LANGUAGE = "language";
    private static final String KEY_LANGUAGE = "zh-tw";

    private static AirService sService;

    private static synchronized AirService getService() {
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
            sService = retrofit.create(AirService.class);
        }
        return sService;
    }

    public static Observable<AcculocationReport> findReportByCity(String type) {
        final Map<String, String> parameters = new HashMap<>();
        parameters.put(Q, type);
        parameters.put(API,KEY_API);
        parameters.put(LANGUAGE,KEY_LANGUAGE);
        return getService().findReport(parameters);
    }

    private interface AirService {
        @GET("locations/v1/cities/geoposition/search")
        Observable<AcculocationReport> findReport(@QueryMap Map<String, String> parameters );
    }
}
