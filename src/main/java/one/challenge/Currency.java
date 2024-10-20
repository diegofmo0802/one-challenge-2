package one.challenge;


public enum Currency {
    USD("United States Dollar"),
    EUR("Euro"),
    JPY("Japanese Yen"),
    GBP("British Pound Sterling"),
    AUD("Australian Dollar"),
    CAD("Canadian Dollar"),
    CHF("Swiss Franc"),
    CNY("Chinese Yuan Renminbi"),
    SEK("Swedish Krona"),
    NZD("New Zealand Dollar"),
    MXN("Mexican Peso"),
    COP("Colombian Peso"),
    CLP("Chilean Peso"),
    ARS("Argentine Peso"),
    UYU("Uruguayan Peso"),
    SGD("Singapore Dollar"),
    HKD("Hong Kong Dollar"),
    NOK("Norwegian Krone"),
    INR("Indian Rupee"),
    RUB("Russian Ruble"),
    ZAR("South African Rand"),
    BRL("Brazilian Real"),
    TRY("Turkish Lira"),
    AED("United Arab Emirates Dirham"),
    THB("Thai Baht"),
    DKK("Danish Krone"),
    PLN("Polish Zloty"),
    TWD("New Taiwan Dollar"),
    ILS("Israeli New Shekel"),
    PHP("Philippine Peso");

    private final String fullName;

    Currency(String fullName) {
        this.fullName = fullName;
    }
    public String getName() {
        return fullName;
    }
    public String getCode() {
        return this.name();
    }
    public static Currency get(int index) {
        Currency[] values = Currency.values();
        if (index < 0 || index >= values.length) return null;
        return values[index];
    }
}
