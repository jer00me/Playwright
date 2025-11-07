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
     * THEN title is "Unsere Seite"
     * AND header is "Unsere Seite" */
    @Test
    @DisplayName("minor: title and header text are 'Unsere Seite'")
    void testTitleAndHeader() {
        assertEquals("Unsere Seite", pageModel.title());
        assertEquals("Unsere Seite", pageModel.getHeaderText());
    }

    /** WHEN fields are empty or partially filled
     *  THEN confirm button remains red (live validation inactive)
     */
    @Test
    @DisplayName("major: confirm button stays red when fields are empty or incomplete")
    void testConfirmButtonStaysRedWhenIncomplete() {
        String initialColor = pageModel.getConfirmButtonColor();
        assertEquals("rgb(255, 0, 0)", initialColor);

        pageModel.setName("Anna");
        assertEquals("rgb(255, 0, 0)", pageModel.getConfirmButtonColor());

        pageModel.setBirthdate(LocalDate.of(2000, 1, 15));
        assertEquals("rgb(255, 0, 0)", pageModel.getConfirmButtonColor());
    }

    /** WHEN all required fields (name, birthdate, color) are filled
     *  THEN confirm button turns green (live validation active)
     */
    @Test
    @DisplayName("major: confirm button turns green when all fields are filled")
    void testConfirmButtonTurnsGreenWhenAllFieldsFilled() {
        pageModel.setName("Anna");
        pageModel.setBirthdate(LocalDate.of(2000, 1, 15));
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
        LocalDate birth = LocalDate.of(1998, 11, 7);

        pageModel.setName(name);
        pageModel.setBirthdate(birth);
        pageModel.selectColor("red"); // red|green|yellow|blue
        assertEquals("rgb(0, 128, 0)", pageModel.getConfirmButtonColor());

        pageModel.clickConfirm();

        int expectedAge = 27;

        String outputText = pageModel.getOutputText();
        assertTrue(outputText.contains("Hallo Clara! Du bist 27 Jahre alt."), "Output should be \"Hallo Clara! Du bist 27 Jahre alt.\"");
        assertTrue(outputText.contains(name), "Output should contain the user name");
        assertTrue(outputText.contains(String.valueOf(expectedAge)), "Output should contain computed age");
    }
    /** GIVEN color=red selected
     *  WHEN I confirm
     *  THEN the name in output is rendered in red
     */
    @Test
    @DisplayName("minor: output name is rendered with selected color (red)")
    void testOutputNameRenderedInSelectedColorRed() {
        String name = "Léa";

        pageModel.setName(name);
        pageModel.setBirthdate(LocalDate.now().minusYears(27));
        pageModel.selectColor("red");

        assertEquals("rgb(0, 128, 0)", pageModel.getConfirmButtonColor());
        pageModel.clickConfirm();

        assertEquals("rgb(255, 0, 0)", pageModel.getOutputNameColor());
    }

    /** GIVEN color=green selected
     *  WHEN I confirm
     *  THEN the name in output is rendered in green
     */
    @Test
    @DisplayName("minor: output name is rendered with selected color (green)")
    void testOutputNameRenderedInSelectedColorGreen() {
        String name = "Manon";

        pageModel.setName(name);
        pageModel.setBirthdate(LocalDate.now().minusYears(27));
        pageModel.selectColor("green");

        assertEquals("rgb(0, 128, 0)", pageModel.getConfirmButtonColor());
        pageModel.clickConfirm();

        assertEquals("rgb(0, 128, 0)", pageModel.getOutputNameColor());
    }

    /** GIVEN color=yellow selected
     *  WHEN I confirm
     *  THEN the name in output is rendered in yellow
     */
    @Test
    @DisplayName("minor: output name is rendered with selected color (yellow)")
    void testOutputNameRenderedInSelectedColorYellow() {
        String name = "Mia";

        pageModel.setName(name);
        pageModel.setBirthdate(LocalDate.now().minusYears(27));
        pageModel.selectColor("yellow");

        assertEquals("rgb(0, 128, 0)", pageModel.getConfirmButtonColor());
        pageModel.clickConfirm();

        assertEquals("rgb(255, 255, 0)", pageModel.getOutputNameColor());
    }


    /** GIVEN color=blue selected
     *  WHEN I confirm
     *  THEN the name in output is rendered in blue
     */
    @Test
    @DisplayName("minor: output name is rendered with selected color (blue)")
    void testOutputNameRenderedInSelectedColorBlue() {
        String name = "Nina";

        pageModel.setName(name);
        pageModel.setBirthdate(LocalDate.now().minusYears(43));
        pageModel.selectColor("blue");

        assertEquals("rgb(0, 128, 0)", pageModel.getConfirmButtonColor());

        pageModel.clickConfirm();

        assertEquals("rgb(0, 0, 255)", pageModel.getOutputNameColor());
    }

    /** WHEN I click the link
     * THEN I navigate to unsere_seite2.html */
    @Test
    @DisplayName("minor: link navigates to 'unsere_seite2.html'")
    void testLinkToSecondPage() {
        pageModel.clickSecondPageLink();
        assertTrue(page.url().endsWith("unsere_seite2.html"));
        UnsereSeite2PageObjectModel second = new UnsereSeite2PageObjectModel(page);
        assertEquals("Unsere Seite 2", second.title(), "Expected page title to match 'Unsere Seite 2' on unsere_seite2.html");
    }

}
