import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class UnsereSeite2Test {

    static Playwright playwright;
    static Browser browser;
    BrowserContext context;
    Page page;

    @BeforeAll
    static void setupBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
    }

    @BeforeEach
    void setupPage() {
        context = browser.newContext();
        page = context.newPage();
        page.navigate("main/webapp/unsere_seite2.html"); 
    }

    @AfterEach
    void closeContext() {
        context.close();
    }

    @AfterAll
    static void closeBrowser() {
        browser.close();
        playwright.close();
    }

    @Test
    void testPageTitle() {
        assertEquals("Unsere Seite 2", page.title());
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
