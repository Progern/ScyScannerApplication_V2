package progernapplications.scyscannerapplication_v2.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Currencies {
    // This is a help class to handle HTTP-Request from ScyScanner API
    // It is used as a holder of currencies list

    @Expose
    @SerializedName("Currencies")
    private List<Currency> currencies;

    public List<Currency> getCurrencies() {
        return currencies;
    }
}
