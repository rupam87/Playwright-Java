package Cucumber;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class ScenarioContext {


    BrowserContext context;
    Page appLandingPage;

    public void setBrowserContext(BrowserContext context){
        this.context = context;
    }

    public BrowserContext getBrowserContext(){
        return this.context;
    }

    public void setAppLandingPage(Page appLandingPage){
        this.appLandingPage = appLandingPage;
    }

    public Page getAppLandingPage(){
        return this.appLandingPage;
    }
}
