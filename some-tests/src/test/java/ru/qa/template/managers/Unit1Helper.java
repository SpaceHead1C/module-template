package ru.qa.template.managers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.qa.template.model.Unit1Data;
import ru.qa.template.model.Units1;

import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Unit1Helper extends HelperBase {
    public Unit1Helper(ApplicationManager app) throws MalformedURLException {
        super(app);
    }

    public void returnToHomePage() {
        click(By.linkText("home page"));
    }

    public void submitUnit1Creation() {
        click(By.name("submit"));
    }

    public void fillUnit1Form(Unit1Data unit1Data, boolean creation) {
        type(By.name("text_field"), unit1Data.getTextField());
        type(By.name("big_text_field"), unit1Data.getBigTextField());
        attach(By.name("photo"), unit1Data.getPhoto());

        if (creation) {
            if (unit1Data.getUnits2().size() > 0) {
                Assert.assertTrue(unit1Data.getUnits2().size() == 1);
                new Select(driver.findElement(By.name("new_unit2")))
                        .selectByVisibleText(unit1Data.getUnits2().iterator().next().getTextField());
            }
        } else {
            Assert.assertFalse(isElementPresent(By.name("new_unit2")));
        }
    }

    public void initUnit1Creation() {
        click(By.linkText("add new"));
    }

    public void deleteSelectedUnits1() {
        click(By.xpath("//input[@value='Delete']"));
    }

    public void selectUnit1ById(int id) {
        driver.findElement(By.cssSelector("input[value='" + id + "']")).click();
    }

    public void initUnit1Modification() {
        click(By.xpath("//img[@alt='Edit']"));
    }

    public void submitUnit1Modification() {
        click(By.name("update"));
    }

    public Unit1Data infoFromEditForm(Unit1Data unit1) {
        initUnit1ModificationById(unit1.getId());
        String text = driver.findElement(By.name("text_field")).getAttribute("value");
        String bigText = driver.findElement(By.name("big_text_field")).getAttribute("value");
        driver.navigate().back();

        return new Unit1Data().withId(unit1.getId()).withTextField(text).withBigTextField(bigText);
    }

    private void initUnit1ModificationById(int id) {
        WebElement checkbox = driver.findElement(By.cssSelector(String.format("input[value='%s']", id)));
        WebElement row = checkbox.findElement(By.xpath("./../.."));
        List<WebElement> cells = row.findElements(By.tagName("td"));
        cells.get(7).findElement(By.tagName("a")).click();
    }

    public void create(Unit1Data unit1) {
        initUnit1Creation();
        fillUnit1Form(unit1, true);
        submitUnit1Creation();
        unit1Cache = null;
        returnToHomePage();
    }

    public void delete(Unit1Data deletedUnit1) {
        selectUnit1ById(deletedUnit1.getId());
        deleteSelectedUnits1();
        unit1Cache = null;
        returnToHomePage();
    }

    public void modify(Unit1Data unit1) {
        selectUnit1ById(unit1.getId());
        initUnit1Modification();
        fillUnit1Form(unit1, false);
        submitUnit1Modification();
        unit1Cache = null;
        returnToHomePage();
    }

    public boolean isThereAContact() {
        return isElementPresent(By.name("selected[]"));
    }

    public int count() {
        return driver.findElements(By.name("selected[]")).size();
    }

    private Units1 unit1Cache = null;

    public Units1 all() {
        Set<Unit1Data> units1 = new HashSet<>();
        List<WebElement> rows = driver.findElements(By.name("entry"));
        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            int id = Integer.parseInt(cells.get(0).findElement(By.tagName("input")).getAttribute("value"));
            String text = cells.get(1).getText();
            String bigText = cells.get(2).getText();
            units1.add(new Unit1Data().withId(id).withTextField(text).withBigTextField(bigText));
        }

        return new Units1(unit1Cache);
    }
}
