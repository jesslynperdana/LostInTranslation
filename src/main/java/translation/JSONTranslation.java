package translation;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONTranslation {

    private final JSONArray jsonArray;
    // public int index = jsonArray.toList().indexOf();

    public JSONTranslation() {
        try {
            String jsonString = Files.readString(Paths.get(getClass().getClassLoader()
                    .getResource("sample.json").toURI()));
            this.jsonArray = new JSONArray(jsonString);
        } catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    public String getCountryNameTranslation(String countryCode, String languageCode) {
        // see the sample.json file to understand the structure of the JSON data
        for (int i = 0; i < this.jsonArray.length(); i++) {
            JSONObject country = this.jsonArray.getJSONObject(i);
            if (country.getString("alpha3").equalsIgnoreCase(countryCode)) {
                if (country.has(languageCode)) {
                    return country.getString(languageCode);
                } else {
                    return "Language not found";
                }
            }
        }
        return "Country not found";

    }
}