package progernapplications.scyscannerapplication_v2.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Markets {

    @Expose
    @SerializedName("Countries")
    private List<MarketCountry> marketsList;

    public List<MarketCountry> getMarkets() {
        return marketsList;
    }
}
