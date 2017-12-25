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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.fail;

public class ApplicationManager {
    private final Properties properties;
    private WebDriver driver;

    private NavigationHelper navigationHelper;
    private Unit1Helper unit1Helper;
    private Unit2Helper unit2Helper;
    private DbHelper dbHelper;
    private FtpHelper ftpHelper;
    private MailHelper mailHelper;

    private StringBuffer verificationErrors = new StringBuffer();
    private String browser;

    public ApplicationManager(String browser) {
        this.browser = browser;
        properties = new Properties();
    }

    public void init() throws IOException {
        String target = System.getProperty("target", "local");
        properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
    }

    public void stop() {
        if (driver != null) {
            driver.quit();
            String verificationErrorString = verificationErrors.toString();
            if (!"".equals(verificationErrorString)) {
                fail(verificationErrorString);
            }
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public WebDriver getDriver() throws MalformedURLException {
        if (driver == null) {
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
        }
        return driver;
    }

    public HttpSession newSession() {
        return new HttpSession(this);
    }

    public Unit1Helper unit1() throws MalformedURLException {
        if (unit1Helper == null) {
            unit1Helper = new Unit1Helper(this);
        }

        return unit1Helper;
    }

    public Unit2Helper unit2() throws MalformedURLException {
        if (unit2Helper == null) {
            unit2Helper = new Unit2Helper(this);
        }

        return unit2Helper;
    }

    public NavigationHelper goTo() throws MalformedURLException {
        if (navigationHelper == null) {
            navigationHelper = new NavigationHelper(this);
        }

        return navigationHelper;
    }

    public DbHelper db() {
        if (dbHelper == null) {
            dbHelper = new DbHelper();
        }
        return dbHelper;
    }

    public FtpHelper ftp() {
        if (ftpHelper == null) {
            ftpHelper = new FtpHelper(this);
        }

        return ftpHelper;
    }

    public MailHelper mail() {
        if (mailHelper == null) {
            mailHelper = new MailHelper(this);
        }

        return mailHelper;
    }

    public byte[] takeScreenshot() {
        return ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
    }
}
