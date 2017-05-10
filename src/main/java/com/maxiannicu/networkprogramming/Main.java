package com.maxiannicu.networkprogramming;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.maxiannicu.networkprogramming.di.ApplicationModule;
import com.maxiannicu.networkprogramming.mail.receiver.MailReceiver;
import com.maxiannicu.networkprogramming.mail.sender.MailSender;
import com.maxiannicu.networkprogramming.utils.Convert;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    private final MailSender mailSender;
    private final Scanner scanner;
    private final MailReceiver mailReceiver;

    @Inject
    public Main(MailSender mailSender, MailReceiver mailReceiver) {
        this.mailSender = mailSender;
        this.mailReceiver = mailReceiver;
        this.scanner = new Scanner(System.in);
    }

    public void run(){
        int option = -1;
        do {
            switch (option){
                case 1 :
                    sendMail();
                    break;
                case 2 :
                    inbox();
                    break;
            }
            System.out.println();
            System.out.println("MENU:");
            System.out.println("1 - Send email");
            System.out.println("2 - Inbox");
            System.out.println();
            System.out.println("0 - Exit");
            System.out.print("Please enter option : ");
        } while ((option = scanner.nextInt()) != 0);

    }

    public void inbox(){
        try {
            Message[] inboxes = mailReceiver.getFolder("Inbox");

            Arrays.stream(inboxes)
                    .forEach(this::printMessage);
        } catch (RuntimeException e){
            System.out.println("Couldn't get inbox. Cause : "+e.getMessage());
        }
    }


    public void sendMail(){
        try {
            System.out.println("Mail sender via SMTP");
            System.out.print("To:");
            String to = scanner.next();

            System.out.print("Subject:");
            String subject = scanner.nextLine();

            System.out.print("Message:");
            String message = scanner.nextLine();
            mailSender.send(to, subject, message);
            System.out.println("Email was sent.");
        } catch (RuntimeException e){
            System.out.println("Couldn't send email. Clause :"+e.getMessage());
        }
    }

    private void printMessage(Message mail) {
        try {
            System.out.println();
            System.out.println("From : " + Convert.addressesToString(mail.getFrom()));
            System.out.println("Received : "+ mail.getReceivedDate().toString());
            System.out.println("Subject : " + mail.getSubject());

        } catch (MessagingException e){
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args){
        Injector injector = Guice.createInjector(new ApplicationModule());
        Main instance = injector.getInstance(Main.class);
        instance.run();
    }
}
