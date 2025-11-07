import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class UnsereSeiteTest extends PlaywrightAbstractTester {

    private UnsereSeitePageObjectModel pageModel;

    @BeforeEach
    void openPage() {
        pageModel = new UnsereSeitePageObjectModel(page);
        pageModel.open();
    }

    /** WHEN I open unsere_seite.html
     * THEN title is "Unsere Seite" and header is "Unsere Seite" */
    @Test
    @DisplayName("minor: title and header text are 'Unsere Seite'")
    void testTitleAndHeader() {
        assertEquals("Unsere Seite", pageModel.title());
        assertEquals("Unsere Seite", pageModel.getHeaderText());
    }

    /** WHEN fields are empty
     * THEN confirm button is red; WHEN all fields filled
     * AND it becomes green (live validation) */
    @Test
    @DisplayName("major: confirm button color transitions (empty→partial→complete)")
    void testConfirmButtonColorChange() {
        String initialColor = pageModel.getConfirmButtonColor(); // rgb()
        assertEquals("rgb(255, 0, 0)", initialColor);

        pageModel.setName("Anna");
        assertEquals("rgb(255, 0, 0)", pageModel.getConfirmButtonColor());

        pageModel.setBirthdate(LocalDate.of(2000, 1, 15));
        assertEquals("rgb(255, 0, 0)", pageModel.getConfirmButtonColor());

        pageModel.selectColor("blue");
        assertEquals("rgb(0, 128, 0)", pageModel.getConfirmButtonColor());
    }

    /** WHEN I click confirm with missing fields
     * THEN an alert appears with the German message */
    @Test
    @DisplayName("major: alert message shown when required fields are missing")
    void testAlertWhenMissingFields() {
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

    /** WHEN all fields are filled and I click confirm
     * THEN output greets with colored name and computed age */
    @Test
    @DisplayName("major: successful confirm renders greeting with colored name and computed age")
    void testSuccessfulConfirmAndOutput() {
        String name = "Clara";
        LocalDate birth = LocalDate.of(1998, 11, 7); // choisis une date fixe de test

        pageModel.setName(name);
        pageModel.setBirthdate(birth);
        pageModel.selectColor("red"); // red|green|yellow|blue
        assertEquals("rgb(0, 128, 0)", pageModel.getConfirmButtonColor()); // green before click

        pageModel.clickConfirm();

        int expectedAge = UnsereSeiteTest.computeAge(birth, LocalDate.now());

        String outputText = pageModel.getOutputText();
        assertTrue(outputText.contains("Hallo"), "Output should start with Hallo");
        assertTrue(outputText.contains(name), "Output should contain the user name");
        assertTrue(outputText.contains(String.valueOf(expectedAge)), "Output should contain computed age");
    }

    /** WHEN I click the link
     * THEN I navigate to unsere_seite2.html */
    @Test
    @DisplayName("minor: link navigates to 'unsere_seite2.html'")
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
