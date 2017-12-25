package ru.qa.template.managers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.qa.template.model.Unit2Data;
import ru.qa.template.model.Units2;

import java.net.MalformedURLException;
import java.util.List;

public class Unit2Helper extends HelperBase {

    public Unit2Helper(ApplicationManager app) throws MalformedURLException {
        super(app);
    }

    public void returnToUnit2Page() {
        click(By.linkText("unit2 page"));
    }

    public void submitUnit2Creation() {
        click(By.name("submit"));
    }

    public void fillUnit2Form(Unit2Data unit2Data) {
        type(By.name("text_field"), unit2Data.getTextField());
    }

    public void initUnit2Creation() {
        click(By.name("new"));
    }

    public void deleteSelectedUnits2() {
        click(By.name("delete"));
    }

    public void selectUnits2ById(int id) {
        driver.findElement(By.cssSelector("input[value='" + id + "']")).click();
    }

    public void initUnits2Modification() {
        click(By.name("edit"));
    }

    public void submitUnits2Modification() {
        click(By.name("update"));
    }

    public void create(Unit2Data unit2) {
        initUnit2Creation();
        fillUnit2Form(unit2);
        submitUnit2Creation();
        unit2Cache = null;
        returnToUnit2Page();
    }

    public void delete(Unit2Data deletedUnit2) {
        selectUnits2ById(deletedUnit2.getId());
        deleteSelectedUnits2();
        unit2Cache = null;
        returnToUnit2Page();
    }

    public void modify(Unit2Data unit2) {
        selectUnits2ById(unit2.getId());
        initUnits2Modification();
        fillUnit2Form(unit2);
        submitUnits2Modification();
        unit2Cache = null;
        returnToUnit2Page();
    }

    public boolean isThereAUnit2() {
        return isElementPresent(By.name("selected[]"));
    }

    public int count() {
        return driver.findElements(By.name("selected[]")).size();
    }

    private Units2 unit2Cache = null;

    public Units2 all() {
        if (unit2Cache != null) {
            return new Units2(unit2Cache);
        }

        unit2Cache = new Units2();
        List<WebElement> elements = driver.findElements(By.cssSelector("span.unit2"));
        for (WebElement element : elements) {
            String name = element.getText();
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
            unit2Cache.add(new Unit2Data().withId(id).withTextField(name));
        }

        return new Units2(unit2Cache);
    }
}
