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
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

public interface RequestService {


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


    // ScyScanner is currently NOT SUPPORTING this feature

    @FormUrlEncoded
    @POST("/apiservices/pricing/v1.0")
    Call<String> createPollSession(
            @Header("Accept") String accept,
            @Header("Content-Type") String contentType,
            @Query("apiKey=") String apikey,
            @Field("country") String country,
            @Field("currency") String currency,
            @Field("locale") String locale,
            @Field("originplace") String originplace,
            @Field("destinationplace") String destinationplace,
            @Field("outbounddate") String outbounddate,
            @Field("inbounddate") String inbounddate
    );



}
