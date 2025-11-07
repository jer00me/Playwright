import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class UnsereSeitePageObjectModel extends AbstractPageObjectModel{

    // elements of the page model, access web elements / locators
    private final Locator href_HelloPage;
    private final Locator h1_Greeting;
    private final Locator button_Language;
    private final Locator input_Number;
    private final Locator button_Square;

    public UnsereSeitePageObjectModel(Page page) {
        super(page);
        this.href_HelloPage = page.locator("#link_pass");
        this.h1_Greeting = page.locator("#heading");
        this.button_Language = page.locator("#button");
        this.input_Number = page.locator("#input");
        this.button_Square = page.locator("#submit");
    }

    public void open() {
        String relativeURL = "/src/main/webapp/unsere_seite2.html";
        String projectRoot = getProjectRoot();
        String url = "file:///" + projectRoot + relativeURL;
        page.navigate(url);
    }

}
