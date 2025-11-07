import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnsereSeite2Test extends PlaywrightAbstractTester{

    private UnsereSeite2PageObjectModel us2;

    @BeforeEach
    void createUnsereSeite2PageObject() {
        us2 = new UnsereSeite2PageObjectModel(page);
        us2.open();
    }

    /**
     * WHEN I open the webpage "unsere_seite2.html" in a Browser
     * THEN the title is "Unsere Seite 2"
     */
    @Test
    @DisplayName("minor: title text of page")
    void testPageTitle() {
        String expectedTitle = "Unsere Seite 2";
        String actualTitle = us2.title();
        assertEquals(expectedTitle, actualTitle);
    }

    /**
     * WHEN I open the webpage "unsere_seite2.html" in a Browser
     * THEN the header is "Unsere Seite 2"
     */
    @Test
    @DisplayName("minor: header text of page")
    void testHeader() {
        String expectedHeader = "Unsere Seite 2";
        String actualHeader = us2.getHeader();
        assertEquals(expectedHeader, actualHeader);
    }

    /**
     * WHEN I select the radio option "Ja"
     * THEN the feedback is "Freut uns zu hören!"
     */
    @Test
    @DisplayName("major: right feedback text when yes selected")
    void testFeedbackYes() {
        us2.clickYes();
        String expectedFeedback = "Freut uns zu hören!";
        String actualFeedback = us2.getFeedback();
        assertEquals(expectedFeedback, actualFeedback);
    }

    /**
     * WHEN I select the radio option "Nein"
     * THEN the feedback is "Schade."
     */
    @Test
    @DisplayName("major: right feedback text when no selected")
    void testFeedbackNo() {
        us2.clickNo();
        String expectedFeedback = "Schade.";
        String actualFeedback = us2.getFeedback();
        assertEquals(expectedFeedback, actualFeedback);
    }
}
