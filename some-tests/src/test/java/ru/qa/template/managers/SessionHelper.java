package ru.qa.template.managers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.net.MalformedURLException;

public class SessionHelper extends HelperBase {
    public SessionHelper(ApplicationManager app) throws MalformedURLException {
        super(app);
    }

    public void login(String username, String password) {
        type(By.name("user"), username);
        type(By.name("pass"), password);
        click(By.xpath("//input[@value='Login']"));
    }
}
