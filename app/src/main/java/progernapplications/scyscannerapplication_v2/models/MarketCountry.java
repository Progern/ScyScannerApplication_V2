package progernapplications.scyscannerapplication_v2.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MarketCountry {

    @Expose
    @SerializedName("Code")
    private String code;

    @Expose
    @SerializedName("Name")
    private String name;

//    public MarketCountry(String code, String name) {
//        this.code = code;
//        this.name = name;
//    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return name;
    }
}
