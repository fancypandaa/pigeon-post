package com.pigeon.post.mail.sender;

import com.pigeon.post.models.SMTPInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
@Slf4j
public class MailSender {
    public static JavaMailSender buildMailSender(SMTPInfo smtpInfo) {
        try {
            log.error("Im Build Your SMTP");
            JavaMailSenderImpl mailSender=new JavaMailSenderImpl();
            mailSender.setHost(smtpInfo.getHostProvider().host);
            mailSender.setPort(smtpInfo.getHostProvider().port);
            mailSender.setUsername(smtpInfo.getEmail());
            mailSender.setPassword(smtpInfo.getPassword());
            Properties props = mailSender.getJavaMailProperties();
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.auth", smtpInfo.getHostProvider().auth);
            props.put("mail.smtp.starttls.enable",smtpInfo.getHostProvider().ttls);
            props.put("mail.debug", "true");
            mailSender.setJavaMailProperties(props);
            return mailSender;
        } catch (MailException mailException){
            mailException.printStackTrace();
            return null;
        }
    }
}
