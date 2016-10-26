package progernapplications.scyscannerapplication_v2.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Locale {

    @Expose
    @SerializedName("Code")
    private String code;

    @Expose
    @SerializedName("Name")
    private String name;

//    public Locale(String code, String name) {
//        this.code = code;
//        this.name = name;
//    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}

