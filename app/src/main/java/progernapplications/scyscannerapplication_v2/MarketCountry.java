package progernapplications.scyscannerapplication_v2;


public class MarketCountry {
    private String code;
    private String name;

    public MarketCountry(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }
}
