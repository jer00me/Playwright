import com.microsoft.playwright.Page;

import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class AbstractPageObjectModel {

    protected Page page;

    protected AbstractPageObjectModel(Page page){
        this.page = page;
    }

    public String title() {
        return page.title();
    }

    protected String getProjectRoot() {
        String projectRoot = null;
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            projectRoot = System.getProperty("user.dir");
        } else {
            Path path = Paths.get("");
            projectRoot = path.toAbsolutePath().toString();
        }
        return projectRoot;
    }

}
