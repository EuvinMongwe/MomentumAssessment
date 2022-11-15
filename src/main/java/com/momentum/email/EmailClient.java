/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.momentum.email;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 *
 * @author euvinmongwe
 */
@Component
public class EmailClient {

    @Autowired
    private JavaMailSender emailSender;

    /**
     *
     * @param to
     * @param subject
     * @param text
     * @param from
     */
    public void sendMail(String to, String subject, String text, String from) {

        try {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom(from);
                message.setTo(to);
                message.setSubject(subject);
                message.setText(text);
                System.out.println(message.toString());
                emailSender.send(message);
       } catch (MailException e) {
            e.printStackTrace();
        }
    }

}
