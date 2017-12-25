package ru.qa.template.managers;


import ru.qa.template.model.MailMessage;

import javax.mail.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MailHelper {
    private ApplicationManager app;

    private Session mailSession;
    private Store store;
    private String mailserver;

    public MailHelper(ApplicationManager app) {
        this.app = app;
        mailserver = app.getProperty("mailserver.host");
        mailSession = Session.getDefaultInstance(System.getProperties());
    }

    public void drainEmail(String username, String password) throws MessagingException {
        Folder inbox = openInbox(username, password);
        for (Message message : inbox.getMessages()) {
            message.setFlag(Flags.Flag.DELETED, true);
        }
        closeFolder(inbox);
    }

    private void closeFolder(Folder folder) throws MessagingException {
        folder.close(true);
        store.close();
    }

    private Folder openInbox(String username, String password) throws MessagingException {
        store = mailSession.getStore("pop3");
        store.connect(mailserver, username, password);
        Folder folder = store.getDefaultFolder().getFolder("INBOX");
        folder.open(Folder.READ_WRITE);

        return folder;
    }

    public List<MailMessage> waitForMail(String username, String password, long timeout) throws MessagingException {
        long now = System.currentTimeMillis();
        while (System.currentTimeMillis() < now + timeout) {
            List<MailMessage> allMail = getAllMail(username, password);
            if (allMail.size() > 0) {
                return allMail;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        throw new Error("No mail");
    }

    public List<MailMessage> getAllMail(String username, String password) throws MessagingException {
        Folder inbox = openInbox(username, password);
        List<MailMessage> messages = Arrays.asList(inbox.getMessages()).stream().map(MailHelper::toModelMail)
                .collect(Collectors.toList());
        closeFolder(inbox);

        return messages;
    }

    private static MailMessage toModelMail(Message m) {
        try {
            return new MailMessage(m.getAllRecipients()[0].toString(), (String)m.getContent());
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
