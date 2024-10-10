import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private static List<String> conversionHistory = new ArrayList<>();
    private static ExchangerateApiRequest apiRequest = new ExchangerateApiRequest();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continueRunning = true;

        while (continueRunning) {
            System.out.println("\n==== MENÚ PRINCIPAL ====");
            System.out.println("1. Realizar una conversión");
            System.out.println("2. Mostrar tasas de cambio de una moneda");
            System.out.println("3. Cambiar API Key");
            System.out.println("4. Guardar historial de conversiones");
            System.out.println("5. Ver historial de conversiones");
            System.out.println("6. Limpiar historial de conversiones");
            System.out.println("7. Salir");
            System.out.print("Elige una opción: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    realizarConversion(scanner);
                    break;
                case 2:
                    mostrarTasasDeCambio(scanner);
                    break;
                case 3:
                    cambiarApiKey(scanner);
                    break;
                case 4:
                    guardarHistorial();
                    break;
                case 5:
                    verHistorial();
                    break;
                case 6:
                    limpiarHistorial();
                    break;
                case 7:
                    continueRunning = false;
                    System.out.println("Saliendo del programa.");
                    break;
                default:
                    System.out.println("Opción no válida. Intenta de nuevo.");
                    break;
            }
        }

        scanner.close();
    }

    private static void realizarConversion(Scanner scanner) {
        System.out.print("Ingresa la cantidad a convertir: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Ingresa la moneda de origen (ej: USD, EUR, COP): ");
        String fromCurrency = scanner.nextLine().toUpperCase();

        System.out.print("Ingresa la moneda de destino (ej: USD, EUR, COP): ");
        String toCurrency = scanner.nextLine().toUpperCase();

        double result = apiRequest.convert(fromCurrency, toCurrency, amount);

        if (result != -1) {
            String conversion = amount + " " + fromCurrency + " equivale a " + result + " " + toCurrency;
            System.out.println(conversion);
            conversionHistory.add(conversion);
        }
    }

    private static void mostrarTasasDeCambio(Scanner scanner) {
        System.out.print("Ingresa la moneda de origen (ej: USD, EUR, COP): ");
        String fromCurrency = scanner.nextLine().toUpperCase();

        ExchangerateApiResponse response = apiRequest.getRatesForCurrency(fromCurrency);
        if (response != null) {
            System.out.println("Tasas de cambio para " + fromCurrency + ":");
            response.getConversionRates().forEach((currency, rate) ->
                    System.out.println("1 " + fromCurrency + " = " + rate + " " + currency));
        }
    }

    private static void cambiarApiKey(Scanner scanner) {
        System.out.print("Ingresa la nueva API Key: ");
        String newApiKey = scanner.nextLine();
        apiRequest.setApiKey(newApiKey);
        System.out.println("API Key cambiada correctamente.");
    }

    private static void guardarHistorial() {
        FileGenerator fileGenerator = new FileGenerator();
        fileGenerator.saveConversionHistory(conversionHistory);
    }

    private static void verHistorial() {
        if (conversionHistory.isEmpty()) {
            System.out.println("El historial está vacío.");
        } else {
            System.out.println("Historial de conversiones:");
            conversionHistory.forEach(System.out::println);
        }
    }

    private static void limpiarHistorial() {
        conversionHistory.clear();
        System.out.println("Historial de conversiones limpiado.");
    }
}
