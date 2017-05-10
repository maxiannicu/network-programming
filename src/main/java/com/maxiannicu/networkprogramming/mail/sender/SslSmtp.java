package com.maxiannicu.networkprogramming.mail.sender;

import com.google.inject.Inject;
import com.maxiannicu.networkprogramming.configuration.Configuration;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by nicu on 5/9/17.
 */
public class SMTP implements MailSender {
    private final Configuration configuration;

    private Session instance;

    @Inject
    public SMTP(Configuration configuration) {
        this.configuration = configuration;

    }

    @Override
    public void send(String to, String subject, String message) {

        try {
            Message msg = new MimeMessage(getSession());
            msg.setFrom(new InternetAddress(configuration.get("smtp.username")));
            msg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));
            msg.setSubject(subject);
            msg.setText(message);

            Transport.send(msg);
        } catch (MessagingException e) {
            throw new RuntimeException("Coludn't send email",e);
        }
    }

    private Properties getProperties() {
        Properties props = new Properties();
        props.put("mail.smtp.host", configuration.get("smtp.host"));
        props.put("mail.smtp.socketFactory.port", configuration.get("smtp.port"));
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", configuration.get("smtp.port"));

        return props;
    }

    private Session getSession(){
        if (instance == null) {
            instance = Session.getInstance(getProperties(), new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(configuration.get("smtp.username"), configuration.get("smtp.password"));
                }
            });
        }

        return instance;
    }
}
