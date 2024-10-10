import com.google.gson.Gson;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;

public class ExchangerateApiRequest {

    private String apiKey = "8b58e220e477330db34d730b";

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public ExchangerateApiResponse getRatesForCurrency(String fromCurrency) {
        try {
            URI uri = URI.create("https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/" + fromCurrency);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .timeout(Duration.ofMinutes(2))
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                Gson gson = new Gson();
                return gson.fromJson(response.body(), ExchangerateApiResponse.class);
            } else {
                System.out.println("Error en la respuesta de la API. Código de estado: " + response.statusCode());
            }

        } catch (Exception e) {
            System.out.println("Se produjo un error al procesar la solicitud: " + e.getMessage());
        }
        return null;
    }

    public double convert(String fromCurrency, String toCurrency, double amount) {
        ExchangerateApiResponse apiResponse = getRatesForCurrency(fromCurrency);
        if (apiResponse != null) {
            Map<String, Double> conversionRates = apiResponse.getConversionRates();
            if (conversionRates.containsKey(toCurrency)) {
                double conversionRate = conversionRates.get(toCurrency);
                return amount * conversionRate;
            } else {
                System.out.println("Error: Moneda de destino no válida.");
            }
        }
        return -1;
    }
}
