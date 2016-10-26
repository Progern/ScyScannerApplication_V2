package progernapplications.scyscannerapplication_v2.services;


import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import progernapplications.scyscannerapplication_v2.config.Config;
import progernapplications.scyscannerapplication_v2.models.Locales;
import progernapplications.scyscannerapplication_v2.models.Markets;
import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Header;

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

    @GET("countries/en-GB?" + Config.API_KEY)
    Call<Markets> getMarketCountries(@Header("Accept") String accept);

}
