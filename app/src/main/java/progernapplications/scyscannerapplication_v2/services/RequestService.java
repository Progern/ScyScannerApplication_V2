package progernapplications.scyscannerapplication_v2.services;


import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import progernapplications.scyscannerapplication_v2.config.Config;
import progernapplications.scyscannerapplication_v2.models.Currencies;
import progernapplications.scyscannerapplication_v2.models.Locales;
import progernapplications.scyscannerapplication_v2.models.Markets;
import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Path;

public interface RequestService {

    // Build
    public static final Gson GSON = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create();
    @NonNull
    public static final Retrofit RETROFIT = new Retrofit.Builder()
            .baseUrl(Config.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GSON))
            .build();

    @GET("locales?" + Config.API_KEY)
    Call<Locales> getLocales(@Header("Accept") String accept);

    @GET("countries/{locale}?" + Config.API_KEY)
    Call<Markets> getMarketCountries(@Header("Accept") String accept, @Path("locale") String locale);

    @GET("currencies?" + Config.API_KEY)
    Call<Currencies> getCurrencies(@Header("Accept") String accept);

}
