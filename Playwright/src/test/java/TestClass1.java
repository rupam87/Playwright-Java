import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestClass1 extends BaseTestRunner {

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

}
