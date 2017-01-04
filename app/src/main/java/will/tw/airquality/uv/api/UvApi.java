package will.tw.airquality.uv.api;

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
import will.tw.airquality.uv.model.UvReport;

public class UvApi {

    private static final String BASE_URL = "http://opendata.epa.gov.tw/";
    private static final String FILTER = "filters";
    private static final String FORMAT = "format";
    private static final String KEY_FORMAT ="json";
    private static final String TOKEN = "token";
    private static final String KEY_TOKEN = "FmMrns2zGE+q6Pqe7s1xpA";

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

    public static Observable<UvReport> findReportByCity(String type) {
        final Map<String, String> parameters = new HashMap<>();
        parameters.put(FILTER, type);
        parameters.put(FORMAT,KEY_FORMAT);
        parameters.put(TOKEN,KEY_TOKEN);
        return getService().findReport(parameters);
    }

    private interface UvService {
        @GET("webapi/api/rest/datastore/355000000I-000004/")
        Observable<UvReport> findReport(@QueryMap Map<String, String> parameters );
    }
}
