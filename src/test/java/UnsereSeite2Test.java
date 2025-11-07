import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class UnsereSeite2Test extends PlaywrightAbstractTester{

    static Playwright playwright;
    static Browser browser;
    BrowserContext context;
    Page page;

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
        page.locator("input[value='yes']").click();
        String feedback = page.locator("#feedback").innerText();
        assertEquals("Freut uns zu hören!", feedback);
    }

    @Test
    void testFeedbackNo() {
        page.locator("input[value='no']").click();
        String feedback = page.locator("#feedback").innerText();
        assertEquals("Schade.", feedback);
    }
}
