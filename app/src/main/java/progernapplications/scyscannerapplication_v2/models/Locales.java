package progernapplications.scyscannerapplication_v2.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Locales {

    // This is a help class to handle HTTP-Request from ScyScanner API
    // It is used as a holder of locales list

    @Expose
    @SerializedName("Locales")
    private List<Locale> locales;

    public List<Locale> getLocales() {
        return locales;
    }
}
