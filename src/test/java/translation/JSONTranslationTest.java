package translation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JSONTranslationTest {
    @Test
    public void testCountryNameTranslation() {
        JSONTranslation translation = new JSONTranslation();
        assertEquals("Indonesien", translation.getCountryNameTranslation("idn","da"));

    }
}
