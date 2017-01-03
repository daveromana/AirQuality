package will.tw.airquality.air.api;

/**
 * Created by william on 2016/12/28.
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
import will.tw.airquality.air.model.AirReport;

public class AirApi {

    private static final String BASE_URL = "http://opendata.epa.gov.tw/";
    private static final String FILTER = "filters";
    private static final String FORMAT = "format";
    private static final String KEY_FORMAT ="json";
    private static final String TOKEN = "token";
    private static final String KEY_TOKEN = "FmMrns2zGE+q6Pqe7s1xpA";

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

    public static Observable<AirReport> findReportByCity(String type) {
        final Map<String, String> parameters = new HashMap<>();
        parameters.put(FILTER, type);
        parameters.put(FORMAT,KEY_FORMAT);
        parameters.put(TOKEN,KEY_TOKEN);
        return getService().findReport(parameters);
    }

    private interface AirService {
        @GET("webapi/api/rest/datastore/355000000I-000001/")
        Observable<AirReport> findReport(@QueryMap Map<String, String> parameters );
    }
}
