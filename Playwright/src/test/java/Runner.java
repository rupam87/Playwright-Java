import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class Runner {


    @Test
    public void setupTest() {
        try (Playwright playwright = Playwright.create()) {
            LaunchOptions launchOptions = new LaunchOptions();
            launchOptions.setHeadless(false);
            List<String> args = new ArrayList<>();
            args.add("--start-maximized");
            launchOptions.setArgs(args);
            Browser browser = playwright.chromium().launch(launchOptions);
            BrowserContext context = browser.newContext();
            Page page = context.newPage();
            page.setViewportSize(1220, 980);
            page.navigate("http://playwright.dev");

            System.out.println(page.title());
        }
    }

}
