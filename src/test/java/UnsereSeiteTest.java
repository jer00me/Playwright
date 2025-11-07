import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UnsereSeiteTest extends PlaywrightAbstractTester {

    private UnsereSeitePageObjectModel pageModel;

    @BeforeEach
    void openPage() {
        pageModel = new UnsereSeitePageObjectModel(page);
        pageModel.open();
    }

    /** WHEN I open unsere_seite.html THEN title is "Unsere Seite" and header is "Unsere Seite" */
    @Test
    void testTitleAndHeader() {
        assertEquals("Unsere Seite", pageModel.title());
        assertEquals("Unsere Seite", pageModel.getHeaderText());
    }

    @Test
    void testConfirmButtonColorChange() {
        // Initial (empty) -> red
        String initialColor = pageModel.getConfirmButtonColor(); // rgb()
        assertEquals("rgb(255, 0, 0)", initialColor);

        // Fill only name -> still red
        pageModel.setName("Anna");
        assertEquals("rgb(255, 0, 0)", pageModel.getConfirmButtonColor());

        // Fill date -> still red (color missing)
        pageModel.setBirthdate(LocalDate.of(2000, 1, 15));
        assertEquals("rgb(255, 0, 0)", pageModel.getConfirmButtonColor());

        // Select color -> turns green
        pageModel.selectColor("blue");
        assertEquals("rgb(0, 128, 0)", pageModel.getConfirmButtonColor());
    }

    void testAlertWhenMissingFields() {
        // Only type name, missing date & color
        pageModel.setName("Max");

        final boolean[] dialogSeen = {false};
        page.onceDialog(d -> {
            dialogSeen[0] = true;
            assertEquals("Bitte alle Felder ausfüllen!", d.message());
            d.accept();
        });

        pageModel.clickConfirm();
        assertTrue(dialogSeen[0], "Expected alert dialog to be shown when fields are missing");
    }

    @Test
    void testSuccessfulConfirmAndOutput() {
        String name = "Clara";
        LocalDate birth = LocalDate.of(1998, 11, 7); // choisis une date fixe de test

        // Fill all
        pageModel.setName(name);
        pageModel.setBirthdate(birth);
        pageModel.selectColor("red"); // red|green|yellow|blue
        assertEquals("rgb(0, 128, 0)", pageModel.getConfirmButtonColor()); // green before click

        // Click
        pageModel.clickConfirm();

        // Compute expected age like the page JS
        int expectedAge = UnsereSeiteTest.computeAge(birth, LocalDate.now());

        // Assert output text (ignore the span color style in the HTML, check text content)
        String outputText = pageModel.getOutputText();
        assertTrue(outputText.contains("Hallo"), "Output should start with Hallo");
        assertTrue(outputText.contains(name), "Output should contain the user name");
        assertTrue(outputText.contains(String.valueOf(expectedAge)), "Output should contain computed age");
    }

    @Test
    void testLinkToSecondPage() {
        pageModel.clickSecondPageLink();
        assertTrue(page.url().endsWith("unsere_seite2.html"));
        UnsereSeite2PageObjectModel second = new UnsereSeite2PageObjectModel(page);
        assertNotNull(second.title());
    }

    private static int computeAge(LocalDate birthDate, LocalDate today) {
        int age = today.getYear() - birthDate.getYear();
        if (today.getMonthValue() < birthDate.getMonthValue()
                || (today.getMonthValue() == birthDate.getMonthValue() && today.getDayOfMonth() < birthDate.getDayOfMonth())) {
            age--;
        }
        return age;
    }
}
