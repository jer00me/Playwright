import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class UnsereSeite2PageObjectModel extends AbstractPageObjectModel{

    // elements of the page model, access web elements / locators
    private final Locator h1Header;
    private final Locator radioYes;
    private final Locator radioNo;
    private final Locator feedback;
    private final Locator hrefUnsereSeite1;

    public UnsereSeite2PageObjectModel(Page page) {
        super(page);
        this.h1Header = page.locator("#header");
        this.radioYes = page.locator("input[value='yes']");
        this.radioNo = page.locator("input[value='no']");
        this.feedback = page.locator("#feedback");
        this.hrefUnsereSeite1 = page.locator("#button");
    }

    public void open() {
        String relativeURL = "/src/main/webapp/unsere_seite2.html";
        String projectRoot = getProjectRoot();
        String url = "file:///" + projectRoot + relativeURL;
        page.navigate(url);
    }

    public String getHeader(){
        return h1Header.textContent();
    }

    public void clickYes() {
        radioYes.click();
    }

    public void clickNo() {
        radioNo.click();
    }

    public String getFeedback() {
        return feedback.textContent();
    }

    public UnsereSeitePageObjectModel clickReturnButton() {
        hrefUnsereSeite1.click();
        return new UnsereSeitePageObjectModel(page);
    }

}
