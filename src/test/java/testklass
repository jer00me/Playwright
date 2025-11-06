import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class HomePageTest {

    static Playwright playwright;
    static Browser browser;
    BrowserContext context;
    Page page;

    @BeforeAll
    static void setupAll() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
    }

    @BeforeEach
    void setup() {
        context = browser.newContext();
        page = context.newPage();
    }

    @AfterEach
    void teardown() {
        context.close();
    }

    @AfterAll
    static void teardownAll() {
        browser.close();
        playwright.close();
    }

    @Test
    void shouldLoadHomePageAndCheckTitle() {
        page.navigate("https://www.htwsaar.de");
        assertTrue(page.title().toLowerCase().contains("htw saar"));
    }
}
