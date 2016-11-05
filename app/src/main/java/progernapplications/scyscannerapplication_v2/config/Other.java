package progernapplications.scyscannerapplication_v2.config;


public class Other
{
    // A class which holds int counters and other stuff
    // Didn't want to carry everything in Config, only necessary API things

    public static int ALERT_COUNTER = 0;
    public static final String SETTINGS_DIALOG_MESSAGE = "If you want to change settings" + "\nthe Language field is REQUIRED!";
    public static int DATE_SET_CHECKER = 0;
    public static int FIELD_ADAPTER_CHECKER = 0;

    public static void setDateSetChecker(int dateSetChecker) {
        DATE_SET_CHECKER = dateSetChecker;
    }

    public static void setFieldAdapterChecker(int fieldAdapterChecker) {
        FIELD_ADAPTER_CHECKER = fieldAdapterChecker;
    }

    public static int getDateSetChecker() {
        return DATE_SET_CHECKER;
    }

    public static int getFieldAdapterChecker() {
        return FIELD_ADAPTER_CHECKER;
    }
}
