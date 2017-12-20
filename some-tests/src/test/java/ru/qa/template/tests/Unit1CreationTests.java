package ru.qa.template.tests;

import org.testng.annotations.Test;
import ru.qa.template.model.Unit1Data;
import ru.qa.template.model.Units2;

import java.io.File;

public class Unit1CreationTests extends TestBase {

    @Test()
    public void testGroupCreation() throws Exception {
        Units2 units2 = app.db().units2();
        File photo = new File("src\\test\\resources\\photo.jpg");
        Unit1Data newUnit1 = new Unit1Data().withTextField("test_text").withBigTextField("test_big_text").withPhoto(photo)
                .inUnit2(units2.iterator().next());
        app.goTo().gotoHomePage();
        app.unit1().initUnit1Creation();

        app.unit1().fillUnit1Form(newUnit1, true);
        app.unit1().submitUnit1Creation();
        app.unit1().returnToHomePage();
    }
}
