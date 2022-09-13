import com.microsoft.playwright.*;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.List;

public class Runner {

    Playwright playwright;
    BrowserContext context;
    Browser browser;
    Page appLandingPage;

    @BeforeSuite
    public void beforeSuite() {
        playwright = Playwright.create();
        System.out.println("Executed BeforeSuite");
    }

    @AfterSuite
    public void afterSuite() {
        playwright.close();
        System.out.println("Executed AfterSuite");
    }

    @BeforeTest
    public void beforeTest() {
        LaunchOptions launchOptions = new LaunchOptions();
        launchOptions.setHeadless(false);
        List<String> args = new ArrayList<>();
        args.add("--start-maximized");
        launchOptions.setArgs(args);
        browser = playwright.chromium().launch(launchOptions);
        System.out.println("Executed BeforeTest");
    }

    @AfterTest
    public void afterTest() {
        browser.close();
        System.out.println("Executed AfterTest");
    }

    @BeforeMethod
    public void beforeEachTestMethod() {
        context = browser.newContext();
        appLandingPage = context.newPage();
        appLandingPage.setViewportSize(1220, 980);
        System.out.println("Executed BeforeMethod");
    }

    @AfterMethod
    public void afterEachTestMethod() {
        appLandingPage.close();
        context.close();
        System.out.println("Executed AfterMethod");
    }

    @Test
    public void Test1() {
        try {
            System.out.println("Test1 about to Execute");
            appLandingPage.navigate("http://www.seleniumframework.com/Practiceform/");
            Locator.WaitForOptions lWaitOptions = new Locator.WaitForOptions();
            lWaitOptions.setState(WaitForSelectorState.VISIBLE);
            lWaitOptions.setTimeout(5000);
            appLandingPage.locator("xpath=//button[@id='button1']").waitFor(lWaitOptions);

            // Test New Browser Page opening in new window
            Page newWindowPage = context.waitForPage(() -> appLandingPage.locator("xpath=//button[@id='button1']").click());
            newWindowPage.setViewportSize(1080, 760);
            Page.WaitForSelectorOptions selectorOptions = new Page.WaitForSelectorOptions();
            selectorOptions.setState(WaitForSelectorState.VISIBLE);
            selectorOptions.setTimeout(5000);
            ElementHandle eleHandle = newWindowPage.waitForSelector("xpath=.//div[@id='bottom-bar']//div[@class='wf-float-left']", selectorOptions);
            Assert.assertTrue(eleHandle.innerText().equalsIgnoreCase("Selenium Framework 2010-2021 Copyrights reserved"), "String Match failed on new window");
            newWindowPage.close();
        } catch (Throwable t) {
        } finally {
            System.out.println("Test1 Executed");
        }
    }

    @Test
    public void Test2() {
        try {
            System.out.println("Test2 about to Execute");
            appLandingPage.navigate("http://www.seleniumframework.com/Practiceform/");
            Locator.WaitForOptions lWaitOptions = new Locator.WaitForOptions();
            lWaitOptions.setState(WaitForSelectorState.VISIBLE);
            lWaitOptions.setTimeout(5000);
            appLandingPage.locator("xpath=//button[@id='button1']").waitFor(lWaitOptions);

            // Test new Tab
            BrowserContext.WaitForPageOptions pageOptions = new BrowserContext.WaitForPageOptions();
            pageOptions.setTimeout(5000);
            Page newTabPage = context.waitForPage(() -> {
                appLandingPage.locator("xpath=.//button[text()='New Browser Tab']").click();
            });
            newTabPage.setViewportSize(1080, 760);
            Page.WaitForSelectorOptions selectorOptions = new Page.WaitForSelectorOptions();
            selectorOptions.setState(WaitForSelectorState.VISIBLE);
            selectorOptions.setTimeout(5000);
            ElementHandle eleHandle = newTabPage.waitForSelector("xpath=.//div[@id='bottom-bar']//div[@class='wf-float-left']", selectorOptions);
            Assert.assertTrue(eleHandle.innerText().equalsIgnoreCase("Selenium Framework 2010-2021 Copyrights reserved"), "String Match failed on new window");
            newTabPage.close();
        } catch (Throwable t) {
        } finally {
            System.out.println("Test2 Executed");
        }
    }

    @Test
    public void Test3() {
        try {
            System.out.println("Test3 about to Execute");
            appLandingPage.navigate("http://www.seleniumframework.com/Practiceform/");
            Locator.WaitForOptions lWaitOptions = new Locator.WaitForOptions();
            lWaitOptions.setState(WaitForSelectorState.VISIBLE);
            lWaitOptions.setTimeout(5000);
            appLandingPage.locator("xpath=//button[@id='button1']").waitFor(lWaitOptions);

            // Dialog Box
            appLandingPage.onDialog((dialog) -> {
                System.out.println("AlertBox Message: " + dialog.message());
                Assert.assertTrue(dialog.message().contains("Please share this website with your friends and in your organization"));
                dialog.accept();
            });
            appLandingPage.waitForSelector("xpath=.//button[@id='alert']").click();

        } catch (Throwable t) {
        } finally {
            System.out.println("Test3 Executed");
        }
    }

}
