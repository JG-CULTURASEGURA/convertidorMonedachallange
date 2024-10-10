import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileGenerator {
    public void fileJson(ExchangerateApiResponse exchangerateApiResponse) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String fileName = exchangerateApiResponse.getBaseCode() + ".json";

        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write(gson.toJson(exchangerateApiResponse));
            System.out.println("Archivo creado: " + fileName);
        } catch (IOException e) {
            System.out.println("Error al crear el archivo JSON: " + e.getMessage());
        }
    }

    public void saveConversionHistory(List<String> history) {
        try (FileWriter fileWriter = new FileWriter("conversion_history.txt", true)) {
            for (String record : history) {
                fileWriter.write(record + "\n");
            }
            System.out.println("Historial guardado en conversion_history.txt");
        } catch (IOException e) {
            System.out.println("Error al guardar el historial: " + e.getMessage());
        }
    }
}
