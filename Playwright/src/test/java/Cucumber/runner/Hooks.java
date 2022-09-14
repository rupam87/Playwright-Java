package Cucumber.runner;

import Cucumber.ScenarioContext;
import com.microsoft.playwright.*;
import io.cucumber.java.*;

import java.util.ArrayList;
import java.util.List;

public class Hooks {
    ScenarioContext scnContext;
    public Playwright playwright;
    public Browser browser = null;

    public Hooks(ScenarioContext scenarioContext){
        this.scnContext = scenarioContext;
    }

    @Before
    public void beforeScenario(){
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
    public void afterScenario(){
        this.scnContext.getBrowserContext().close();
        browser.close();
        playwright.close();
        System.out.println("Executed afterScenario");
    }


}
