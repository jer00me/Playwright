import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public abstract class PlaywrightAbstractTester {

    private final static boolean headless = false;
    private final static boolean chrome = false;

    private static Playwright playwright;
    private static Browser browser;
    private BrowserContext context;
    protected Page page;


    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();
        BrowserType browserType = null;
        if (chrome) {
            browserType = playwright.chromium();
        } else {
            browserType = playwright.firefox();
        }
        final Integer setSlowMo = 700;
        browser = browserType.launch(new BrowserType.LaunchOptions().setHeadless(headless).setSlowMo(setSlowMo));
    }

    @AfterAll
    static void closeBrowser() {
        playwright.close();
    }

    // @BeforeEach methods declared as interface default methods are inherited as long as they are not overridden
    // see: https://junit.org/junit5/docs/5.5.2/api/org/junit/jupiter/api/BeforeEach.html
    @BeforeEach
    void createContextAndPage() {
        context = browser.newContext();
        page = context.newPage();
    }

    @AfterEach
    void closeContext() {
        context.close();
    }

}
