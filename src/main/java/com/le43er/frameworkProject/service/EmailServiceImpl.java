package com.le43er.frameworkProject.service;

import lombok.NoArgsConstructor;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class EmailServiceImpl {
    private final Log log = LogFactory.getLog(this.getClass());

    private JavaMailSender javaMailSender;

    @Autowired
    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendRegistrationMessage(String email, String name){
        SimpleMailMessage message = null;
        try{
            message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Sikeres regisztráció");
            message.setText("Kedves " + name + "! \n \n Ezentúl nem okoz fejfájást a teendőid megjegyzése. \n \n Köszönjük a regisztrációdat!");
            javaMailSender.send(message);
        } catch(Exception ex){
            log.error("Hiba az email küldése közben: "+email,ex);
        }
    }
}
