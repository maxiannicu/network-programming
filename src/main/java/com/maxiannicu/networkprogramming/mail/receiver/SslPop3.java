package com.maxiannicu.networkprogramming.mail.receiver;

import com.google.inject.Inject;
import com.maxiannicu.networkprogramming.configuration.Configuration;
import com.sun.mail.pop3.POP3SSLStore;

import javax.mail.*;
import java.util.Properties;

/**
 * Created by nicu on 5/10/17.
 */
public class SslPop3 implements MailReceiver {
    public static final int MAX_SIZE = 100;
    private final Configuration configuration;

    private Store store;

    @Inject
    public SslPop3(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public Message[] getFolder(String folderName) {
        try {
            Folder folder = getStore().getFolder(folderName);
            folder.open(Folder.READ_ONLY);

            return folder.getMessages();
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
