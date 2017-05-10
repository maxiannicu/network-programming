package com.maxiannicu.networkprogramming.mail.receiver;

import com.google.inject.Inject;
import com.maxiannicu.networkprogramming.configuration.Configuration;
import com.maxiannicu.networkprogramming.entity.Mail;
import com.sun.mail.pop3.POP3SSLStore;

import javax.mail.*;
import java.util.Arrays;
import java.util.Properties;
import java.util.StringJoiner;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by nicu on 5/10/17.
 */
public class Pop3 implements MailReceiver {
    private final Configuration configuration;

    private Store store;

    @Inject
    public Pop3(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public Mail[] getFolder(String folderName) {
        try {
            Folder folder = getStore().getFolder(folderName);
            folder.open(Folder.READ_ONLY);

            // get the list of folder messages
            Message[] messages = folder.getMessages();

            return Arrays.stream(messages).map(this::map).toArray(Mail[]::new);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private Mail map(Message message) {
        try {
            String from = Arrays.stream(message.getFrom()).map(Address::toString).collect(Collectors.joining(","));
            String to = configuration.get("pop.username");
            String subject = message.getSubject();
            return new Mail(from, to, subject, "");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private Store getStore() throws MessagingException {
        if (store == null) {
            Properties properties = getProperties();
            Session session = Session.getDefaultInstance(properties);

            URLName url = new URLName("pop3", configuration.get("pop.host"), Integer.parseInt(configuration.get("pop.port")), "",
                    configuration.get("pop.username"), configuration.get("pop.password"));
            store = new POP3SSLStore(session, url);
            store.connect();
        }

        return store;
    }

    private Properties getProperties() {
        Properties properties = new Properties();
        properties.setProperty("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.pop3.socketFactory.fallback", "false");
        properties.setProperty("mail.pop3.port", configuration.get("pop.port"));
        properties.setProperty("mail.pop3.socketFactory.port", configuration.get("pop.port"));

        return System.getProperties();
    }
}
