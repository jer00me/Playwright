import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class SimpleSamplePageObjectModel extends AbstractPageObjectModel {

    // elements of the page model, access web elements / locators
    private final Locator href_HelloPage;
    private final Locator h1_Greeting;
    private final Locator button_Language;
    private final Locator input_Number;
    private final Locator button_Square;

    public SimpleSamplePageObjectModel(Page page) {
        super(page);
        this.href_HelloPage = page.locator("#link_pass");
        this.h1_Greeting = page.locator("#heading");
        this.button_Language = page.locator("#button");
        this.input_Number = page.locator("#input");
        this.button_Square = page.locator("#submit");
    }

    public void open() {
        String relativeURL = "/src/main/webapp/Playwright_Simple_Sample.html";
        String projectRoot = getProjectRoot();
        String url = "file:///" + projectRoot + relativeURL;
        page.navigate(url);
    }

    public HelloPageObjectModel clickLink() {
        href_HelloPage.click();
        return new HelloPageObjectModel(page);
    }

    public void clickLanguageButton() {
        button_Language.click();
    }

    public String getGreeting() {
        return h1_Greeting.textContent();
    }

    public void typeNumber(int number) {
        input_Number.click();
        input_Number.fill(String.valueOf(number));
    }

    public void clickSquare() {
        button_Square.click();
    }

    public int getNumber() {
        String text = input_Number.inputValue();
        return Integer.parseInt(text);
    }
}
