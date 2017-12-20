package ru.qa.template.tests;

import org.openqa.selenium.remote.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.annotations.*;
import ru.qa.template.managers.ApplicationManager;
import ru.qa.template.model.Unit2Data;
import ru.qa.template.model.Units2;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@Listeners(MyTestListener.class)
public class TestBase {
    Logger logger = LoggerFactory.getLogger(TestBase.class);
    protected static ApplicationManager app = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));

    @BeforeSuite(alwaysRun = true)
    public void setUp(ITestContext context) throws Exception {
        app.init();
        context.setAttribute("app", app);
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() throws Exception {
        app.stop();
    }

    @BeforeMethod
    public void logTestStart(Method m, Object[] p) {
        logger.info("Start test " + m.getName() + " with parameters " + Arrays.asList(p));
    }

    @AfterMethod(alwaysRun = true)
    public void logTestStop(Method m) {
        logger.info("Stop test " + m.getName());
    }

    public void verifyUnit2ListInUI() {
        if (Boolean.getBoolean("verifyUI")) {
            Units2 dbUnits2 = app.db().units2();
            Units2 uiUnits2 = app.unit2().all();
            assertThat(uiUnits2, equalTo(dbUnits2.stream()
                    .map((g) -> new Unit2Data().withId(g.getId()).withTextField(g.getTextField()))
                    .collect(Collectors.toSet())));
        }
    }
}
