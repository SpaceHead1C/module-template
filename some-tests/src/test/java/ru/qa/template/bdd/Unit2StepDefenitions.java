package ru.qa.template.bdd;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.remote.BrowserType;
import ru.qa.template.managers.ApplicationManager;
import ru.qa.template.model.Unit2Data;
import ru.qa.template.model.Units2;

import java.io.IOException;
import java.net.MalformedURLException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class Unit2StepDefenitions {
    private ApplicationManager app;
    private Units2 units2;
    private Unit2Data newUnit2;

    @Before
    public void init() throws IOException {
        app = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));
        app.init();
    }

    @After
    public void stop() {
        app.stop();
        app = null;
    }

    @Given("^a set of units2$")
    public void loadUnits2() {
        units2 = app.db().units2();
    }

    @When("^I create a new unit2 with text (.+) and some parameter (.+)$")
    public void createUnit2(String text, String someParameter) throws MalformedURLException {
        newUnit2 = new Unit2Data().withTextField(text);
        app.goTo().unit2Page();
        app.unit2().create(newUnit2);
    }

    @Then("^the new set of units2 is equal to the old set with the added unit$")
    public void verifyUnit2Created() {
        Units2 newUnits2 = app.db().units2();
        assertThat(newUnits2, equalTo(
                units2.withAdded(newUnit2.withId(newUnits2.stream().mapToInt(Unit2Data::getId).max().getAsInt()))));
    }
}
