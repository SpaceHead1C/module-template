package ru.qa.template.managers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NavigationHelper extends HelperBase {
    public NavigationHelper(WebDriver driver) {
        super(driver);
    }

    public void unit2Page() {
        if (isElementPresent(By.tagName("h1"))
                && driver.findElement(By.tagName("h1")).getText().equals("Units2")
                && isElementPresent(By.name("new"))) {
            return;
        }
        click(By.linkText("units2Page"));
    }

    public void gotoHomePage() {
        if (isElementPresent(By.id("someElementId"))) {
            return;
        }
        click(By.linkText("home"));
    }
}
