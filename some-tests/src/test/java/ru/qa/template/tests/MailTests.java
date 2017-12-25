package ru.qa.template.tests;


import org.testng.annotations.Test;
import ru.qa.template.model.MailMessage;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

public class MailTests extends TestBase {

    @Test
    public void testRegistration() throws MessagingException, IOException {
        long now = System.currentTimeMillis();
        String user = String.format("user%s", now);
        String password = "password";
        String email = String.format("user%s@localhost.localdomain", now);
        List<MailMessage> mailMessages = app.mail().waitForMail(user, password, 60000);
    }
}
