package progernapplications.scyscannerapplication_v2.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Currency {

    @Expose
    @SerializedName("Code")
    private String code;

    @Expose
    @SerializedName("Symbol")
    private String symbol;

//    public Currency(String code, String symbol) {
//        this.code = code;
//        this.symbol = symbol;
//    }

    public String getCode() {
        return code;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return code;
    }
}
