package com.maxiannicu.networkprogramming.di;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Scopes;
import com.maxiannicu.networkprogramming.configuration.Configuration;
import com.maxiannicu.networkprogramming.configuration.JsonConfiguration;
import com.maxiannicu.networkprogramming.mail.receiver.MailReceiver;
import com.maxiannicu.networkprogramming.mail.receiver.SslPop3;
import com.maxiannicu.networkprogramming.mail.sender.MailSender;
import com.maxiannicu.networkprogramming.mail.sender.SslSmtp;

/**
 * Created by nicu on 3/18/17.
 */
public class ApplicationModule implements Module {
    @Override
    public void configure(Binder binder) {
        binder.bind(Configuration.class).to(JsonConfiguration.class).in(Scopes.SINGLETON);
        binder.bind(MailSender.class).to(SslSmtp.class).in(Scopes.SINGLETON);
        binder.bind(MailReceiver.class).to(SslPop3.class).in(Scopes.SINGLETON);
    }
}
