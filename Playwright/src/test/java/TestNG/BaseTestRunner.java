package TestNG;

import com.microsoft.playwright.*;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.List;

public class BaseTestRunner {

    Playwright playwright;
    BrowserContext context;
    Browser browser;
    Page appLandingPage;

    @BeforeSuite
    public void beforeSuite() {
        System.out.println("Executed BeforeSuite");
    }

    @AfterSuite
    public void afterSuite() {
        System.out.println("Executed AfterSuite");
    }

    @BeforeTest
    public void beforeTest() {
        System.out.println("Executed BeforeTest");
    }

    @AfterTest
    public void afterTest() {
        System.out.println("Executed AfterTest");
    }

    @BeforeClass
    public void beforeEachTestClass(){
        playwright = Playwright.create();
        LaunchOptions launchOptions = new LaunchOptions();
        launchOptions.setHeadless(false);
        List<String> args = new ArrayList<>();
        args.add("--start-maximized");
        launchOptions.setArgs(args);
        browser = playwright.chromium().launch(launchOptions);
        System.out.println("Executed beforeEachTestClass");
    }

    @AfterClass
    public void afterEachTestClass(){
        browser.close();
        playwright.close();
        System.out.println("Executed afterEachTestClass");
    }

    @BeforeMethod
    public void beforeEachTestMethod() {
        context = browser.newContext();
        appLandingPage = context.newPage();
        appLandingPage.setViewportSize(1220, 980);
        System.out.println("Executed beforeEachTestMethod");
    }

    @AfterMethod
    public void afterEachTestMethod() {
        appLandingPage.close();
        context.close();
        System.out.println("Executed afterEachTestMethod");
    }

}
