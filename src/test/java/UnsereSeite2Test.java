import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnsereSeite2Test extends PlaywrightAbstractTester{

    private UnsereSeite2PageObjectModel us2;

    @BeforeEach
    void createUnsereSeite2PageObject() {
        us2 = new UnsereSeite2PageObjectModel(page);
        us2.open();
    }

    @Test
    void testPageTitle() {
        assertEquals("Unsere Seite 2", us2.title());
    }

    @Test
    void testFeedbackYes() {
        us2.clickYes();
        String expectedFeedback = "Freut uns zu hören!";
        String actualFeedback = us2.getFeedback();
        assertEquals(expectedFeedback, actualFeedback);
    }

    @Test
    void testFeedbackNo() {
        us2.clickNo();
        String expectedFeedback = "Schade.";
        String actualFeedback = us2.getFeedback();
        assertEquals(expectedFeedback, actualFeedback);
    }
}
