package progernapplications.scyscannerapplication_v2;


import com.google.gson.annotations.SerializedName;

public class Locale {

    @SerializedName("Code")
    private String code;

    @SerializedName("Name")
    private String name;

    public Locale(String code, String name) {
        this.code = code;
        this.name = name;
    }

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

