package ada.osc.movielist;

import android.app.Application;

import ada.osc.movielist.network.ApiService;
import ada.osc.movielist.network.RetrofitUtil;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Retrofit;

/**
 * Created by avukelic on 20-Jun-18.
 */
public class App extends Application {

    Retrofit retrofit;
    static ApiService apiService;

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .name("Tsv2018.ralm")
                .schemaVersion(6)
                .build();
        Realm.setDefaultConfiguration(realmConfig);

        retrofit = RetrofitUtil.createRetrofit();
        apiService = retrofit.create(ApiService.class);


    }

    public static ApiService getApiService() {
        return apiService;
    }
}
