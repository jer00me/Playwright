import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UnsereSeitePageObjectModel extends AbstractPageObjectModel {

    private final Locator header;
    private final Locator nameInput;
    private final Locator birthdateInput;
    private final Locator colorSelect;
    private final Locator confirmBtn;
    private final Locator outputDiv;
    private final Locator linkToSecond;

    public UnsereSeitePageObjectModel(Page page) {
        super(page);
        this.header = page.locator("#header");
        this.nameInput = page.locator("#name");
        this.birthdateInput = page.locator("#birthdate");
        this.colorSelect = page.locator("#color");
        this.confirmBtn = page.locator("#confirmBtn");
        this.outputDiv = page.locator("#output");
        this.linkToSecond = page.locator("#link");
    }

    public void open() {
        String relativeURL = "/src/main/webapp/unsere_seite.html"; // ✅ corrige ici
        String projectRoot = getProjectRoot();
        String url = "file:///" + projectRoot + relativeURL;
        page.navigate(url);
    }

    public String getHeaderText() {
        return header.textContent().trim();
    }

    public void setName(String name) {
        nameInput.fill(name);
    }

    public void setBirthdate(LocalDate date) {
        String iso = date.format(DateTimeFormatter.ISO_LOCAL_DATE);
        birthdateInput.fill(iso);
    }

    public void selectColor(String color) {
        colorSelect.selectOption(color);
    }

    public void clickConfirm() {
        confirmBtn.click();
    }

    public String getConfirmButtonColor() {
        return confirmBtn.evaluate("el => getComputedStyle(el).backgroundColor").toString();
    }

    public String getOutputText() {
        return outputDiv.textContent().trim();
    }

    public void clickSecondPageLink() {
        linkToSecond.click();
    }
}
