package progernapplications.scyscannerapplication_v2.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Places {
    // This is a help class to handle HTTP-Request from ScyScanner API
    // It is used as a holder of places list, proposed by auto suggest places api part

    @Expose
    @SerializedName("Places")
    private List<Place> places;

    public List<Place> getPlaces() {
        return places;
    }
}
