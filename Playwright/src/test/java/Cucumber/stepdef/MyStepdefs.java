package Cucumber.stepdef;

import Cucumber.ScenarioContext;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import io.cucumber.java.en.*;
import org.testng.Assert;

public class MyStepdefs {
    ScenarioContext scenarioContext;

    public MyStepdefs(ScenarioContext scnContext) {
        this.scenarioContext = scnContext;
    }

    @When("I test new browser page in new window")
    public void i_test_new_browser_page_in_new_window() {
        // Test New Browser Page opening in new window
        Page newWindowPage = this.scenarioContext.getBrowserContext().waitForPage(() -> this.scenarioContext.getAppLandingPage().locator("xpath=//button[@id='button1']").click());
        newWindowPage.setViewportSize(1080, 760);
        Page.WaitForSelectorOptions selectorOptions = new Page.WaitForSelectorOptions();
        selectorOptions.setState(WaitForSelectorState.VISIBLE);
        selectorOptions.setTimeout(5000);
        ElementHandle eleHandle = newWindowPage.waitForSelector("xpath=.//div[@id='bottom-bar']//div[@class='wf-float-left']", selectorOptions);
        Assert.assertTrue(eleHandle.innerText().equalsIgnoreCase("Selenium Framework 2010-2021 Copyrights reserved"), "String Match failed on new window");
        newWindowPage.close();
    }

    @Given("I open the url {string}")
    public void i_open_the_url(String url) {
        this.scenarioContext.getAppLandingPage().navigate(url);
        Locator.WaitForOptions lWaitOptions = new Locator.WaitForOptions();
        lWaitOptions.setState(WaitForSelectorState.VISIBLE);
        lWaitOptions.setTimeout(5000);
        this.scenarioContext.getAppLandingPage().locator("xpath=//button[@id='button1']").waitFor(lWaitOptions);
    }


    @When("I test new tab")
    public void i_test_new_tab() {
        // Test new Tab
        BrowserContext.WaitForPageOptions pageOptions = new BrowserContext.WaitForPageOptions();
        pageOptions.setTimeout(5000);
        Page newTabPage = this.scenarioContext.getBrowserContext().waitForPage(() -> {
            this.scenarioContext.getAppLandingPage().locator("xpath=.//button[text()='New Browser Tab']").click();
        });
        newTabPage.setViewportSize(1080, 760);
        Page.WaitForSelectorOptions selectorOptions = new Page.WaitForSelectorOptions();
        selectorOptions.setState(WaitForSelectorState.VISIBLE);
        selectorOptions.setTimeout(5000);
        ElementHandle eleHandle = newTabPage.waitForSelector("xpath=.//div[@id='bottom-bar']//div[@class='wf-float-left']", selectorOptions);
        Assert.assertTrue(eleHandle.innerText().equalsIgnoreCase("Selenium Framework 2010-2021 Copyrights reserved"), "String Match failed on new window");
        newTabPage.close();
    }

    @When("I test new dialog and accept it")
    public void iTestNewDialogAndAcceptIt() {
        // Dialog Box
        this.scenarioContext.getAppLandingPage().onDialog((dialog) -> {
            System.out.println("AlertBox Message: " + dialog.message());
            Assert.assertTrue(dialog.message().contains("Please share this website with your friends and in your organization"));
            dialog.accept();
        });
        this.scenarioContext.getAppLandingPage().waitForSelector("xpath=.//button[@id='alert']").click();
    }
}
