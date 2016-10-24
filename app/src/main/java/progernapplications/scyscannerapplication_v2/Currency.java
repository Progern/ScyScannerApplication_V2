package progernapplications.scyscannerapplication_v2;


public class Currency {
    private String code;
    private String symbol;

    public Currency(String code, String symbol) {
        this.code = code;
        this.symbol = symbol;
    }

    public String getCode() {
        return code;
    }

    public String getSymbol() {
        return symbol;
    }
}
