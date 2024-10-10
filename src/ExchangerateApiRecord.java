class ExchangerateApiResponse {
    private String base_code;
    private String result;
    private String time_last_update_utc;
    private String time_next_update_utc;
    private java.util.Map<String, Double> conversion_rates;

    public String getBaseCode() {
        return base_code;
    }

    public java.util.Map<String, Double> getConversionRates() {
        return conversion_rates;
    }
}
