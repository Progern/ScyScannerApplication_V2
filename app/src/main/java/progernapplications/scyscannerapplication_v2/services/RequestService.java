package progernapplications.scyscannerapplication_v2.services;


import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import progernapplications.scyscannerapplication_v2.config.Config;
import progernapplications.scyscannerapplication_v2.models.Currencies;
import progernapplications.scyscannerapplication_v2.models.Locales;
import progernapplications.scyscannerapplication_v2.models.Markets;
import progernapplications.scyscannerapplication_v2.models.Places;
import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Path;
import retrofit.http.Query;

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


    // Request to get all supported by ScyScanner languages
    @GET("reference/v1.0/locales?" + Config.API_KEY)
    Call<Locales> getLocales(@Header("Accept") String accept);

    // Request, to get market countries, supported by ScyScanner
    @GET("reference/v1.0/countries/{locale}?" + Config.API_KEY)
    Call<Markets> getMarketCountries(@Header("Accept") String accept, @Path("locale") String locale);

    // Request, to get all currencies, supported by ScyScanner
    @GET("reference/v1.0/currencies?" + Config.API_KEY)
    Call<Currencies> getCurrencies(@Header("Accept") String accept);

    //Requst, which will support places suggesting while user will input data
    @GET("autosuggest/v1.0/{market}/{currency}/{locale}/?query=&apiKey=" + Config.API_KEY)
    Call<Places> getAutoSuggestPlaces(@Header("Accept") String accept, @Path("market") String marketCountry, @Path("currency") String currency,
                                      @Path("locale") String locale, @Query("query") String query);



}
