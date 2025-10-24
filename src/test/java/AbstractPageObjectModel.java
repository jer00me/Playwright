import com.microsoft.playwright.Page;

public abstract class AbstractPageObjectModel {

    protected Page page;

    protected AbstractPageObjectModel(Page page){
        this.page = page;
    }
}
