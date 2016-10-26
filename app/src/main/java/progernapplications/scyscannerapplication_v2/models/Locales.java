package progernapplications.scyscannerapplication_v2.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Locales {

    @Expose
    @SerializedName("Locales")
    private List<Locale> locales;

    public List<Locale> getLocales() {
        return locales;
    }
}
