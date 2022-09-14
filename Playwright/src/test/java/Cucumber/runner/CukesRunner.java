package Cucumber.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import org.apache.commons.io.FileUtils;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@CucumberOptions(
        features = "src/test/java/Cucumber/features/",
        glue = {"Cucumber.stepdef","Cucumber.runner"},
        tags = "@testtag",
        plugin = {"json:target/cucumber-reports/CucumberTestReport.json","pretty"
        })
public class CukesRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }

    @AfterSuite
    public void generateCucumberReportHtml(){
        System.out.println("Executing AfterSuite");
        java.util.Collection <File> jsonFiles = FileUtils.listFiles(new File("target/cucumber-reports"), new String[] {"json"}, true);
        List<String> jsonPath = new ArrayList<>();
        jsonFiles.forEach(f -> {
            jsonPath.add(f.getAbsolutePath());
        });
        Configuration config = new Configuration(new File("target/cucumber-reports"), "Playwright-Cukes");
        ReportBuilder reportBuilder = new ReportBuilder(jsonPath, config);
        reportBuilder.generateReports();
    }

}
