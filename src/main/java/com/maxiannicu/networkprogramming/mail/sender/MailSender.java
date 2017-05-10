package com.maxiannicu.networkprogramming.mail.sender;

/**
 * Created by nicu on 5/9/17.
 */
public interface MailSender {
    void send(String to,String subject,String message);
}
