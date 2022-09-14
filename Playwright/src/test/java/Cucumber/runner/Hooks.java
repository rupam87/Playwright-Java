package Cucumber.runner;

import Cucumber.ScenarioContext;
import com.microsoft.playwright.*;
import io.cucumber.java.*;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Hooks {
    ScenarioContext scnContext;
    public Playwright playwright;
    public Browser browser = null;

    public Hooks(ScenarioContext scenarioContext){
        this.scnContext = scenarioContext;
    }

    @Before
    public void beforeScenario(Scenario scenario){
        playwright = Playwright.create();
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions();
        launchOptions.setHeadless(false);
        List<String> args = new ArrayList<>();
        args.add("--start-maximized");
        launchOptions.setArgs(args);
        browser = playwright.chromium().launch(launchOptions);
        this.scnContext.setBrowserContext(browser.newContext());
        this.scnContext.setAppLandingPage(scnContext.getBrowserContext().newPage());
        this.scnContext.getAppLandingPage().setViewportSize(1220, 980);
        System.out.println("Executed beforeScenario");
    }

    @After
    public void afterScenario(Scenario scenario){
        // Capture screenshot if scenario failed
        if (scenario.isFailed()) {
            Page.ScreenshotOptions screenshotOptions = new Page.ScreenshotOptions();
            String screenshotPath= System.getProperty("user.dir") + "\\target\\cucumber-reports\\screenshot\\" + DateTime.now().toString() + ".png";
            screenshotOptions.setFullPage(true).setPath(Paths.get(screenshotPath.replaceAll(":","")).toAbsolutePath());
            byte[] screenshot = this.scnContext.getAppLandingPage().screenshot(screenshotOptions);
            scenario.attach(screenshot, "image/png", scenario.getName());
        }


        this.scnContext.getBrowserContext().close();
        browser.close();
        playwright.close();

        System.out.println("Executed afterScenario");
    }


}
