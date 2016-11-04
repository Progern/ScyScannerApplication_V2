package progernapplications.scyscannerapplication_v2.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Place {

    @Expose
    @SerializedName("PlaceName")
    private String name;

    @Expose
    @SerializedName("PlaceId")
    private String Id;

    @Expose
    @SerializedName("CountryName")
    private String countryName;

    public String getName() {
        return name;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getId() {
        return Id;
    }

    @Override
    public String toString() {
        return name + "(" + Id + ")";
    }
}
