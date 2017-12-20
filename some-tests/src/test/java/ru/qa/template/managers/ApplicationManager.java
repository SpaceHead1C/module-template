package ru.qa.template.managers;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.fail;

public class ApplicationManager {
    private final Properties properties;
    protected WebDriver driver;

    private NavigationHelper navigationHelper;
    private Unit1Helper unit1Helper;
    private Unit2Helper unit2Helper;
    private SessionHelper sessionHelper;
    private DbHelper dbHelper;

    private StringBuffer verificationErrors = new StringBuffer();
    private String browser;

    public ApplicationManager(String browser) {
        this.browser = browser;
        properties = new Properties();
    }

    public void init() throws IOException {
        String target = System.getProperty("target", "local");
        properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));

        dbHelper = new DbHelper();

        if ("".equals(properties.getProperty("selenium.server"))) {
            if (browser.equals(BrowserType.FIREFOX)) {
                System.setProperty("webdriver.gecko.driver", "C:\\path_to\\geckodriver.exe");
                driver = new FirefoxDriver();
            } else if (browser.equals(BrowserType.CHROME)) {
                System.setProperty("webdriver.chrome.driver", "C:\\path_to\\chromedriver.exe");
                driver = new ChromeDriver();
            } else if (browser.equals(BrowserType.IE)) {
                System.setProperty("webdriver.ie.driver", "C:\\path_to\\IEDriverServer.exe");
                driver = new InternetExplorerDriver();
            }
        } else {
            DesiredCapabilities capabilities = new DesiredCapabilities();

            if (browser.equals(BrowserType.FIREFOX)) {
                capabilities = DesiredCapabilities.firefox();
            } else if (browser.equals(BrowserType.CHROME)) {
                capabilities = DesiredCapabilities.chrome();
            } else if (browser.equals(BrowserType.IE)) {
                capabilities = DesiredCapabilities.internetExplorer();
            }

            capabilities.setPlatform(Platform.fromString(System.getProperty("platform", "win8.1")));
            driver = new RemoteWebDriver(new URL(properties.getProperty("selenium.server")), capabilities);
        }

        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        driver.get(properties.getProperty("web.baseUri"));

        sessionHelper = new SessionHelper(driver);
        navigationHelper = new NavigationHelper(driver);
        unit1Helper = new Unit1Helper(driver);
        unit2Helper = new Unit2Helper(driver);

        sessionHelper.login(properties.getProperty("web.adminLogin"), properties.getProperty("web.adminPassword"));
    }

    public void stop() {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    public Unit1Helper unit1() {
        return unit1Helper;
    }

    public Unit2Helper unit2() {
        return unit2Helper;
    }

    public NavigationHelper goTo() {
        return navigationHelper;
    }

    public DbHelper db() {
        return dbHelper;
    }

    public byte[] takeScreenshot() {
        return ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
    }
}
