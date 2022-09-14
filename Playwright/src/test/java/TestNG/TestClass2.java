package TestNG;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestClass2 extends BaseTestRunner {

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
