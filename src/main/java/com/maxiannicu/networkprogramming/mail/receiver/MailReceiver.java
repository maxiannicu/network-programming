package com.maxiannicu.networkprogramming.mail.receiver;

import javax.mail.Message;

/**
 * Created by nicu on 5/10/17.
 */
public interface MailReceiver {
    Message[] getFolder(String folder);
}
