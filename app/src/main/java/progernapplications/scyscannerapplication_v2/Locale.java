package progernapplications.scyscannerapplication_v2;


public class Locale {
    private String code;
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
}

