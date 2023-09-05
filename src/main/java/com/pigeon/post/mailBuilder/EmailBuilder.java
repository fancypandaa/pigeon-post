package com.pigeon.post.mailBuilder;

import com.pigeon.post.mail.sender.MailSubject;
import com.pigeon.post.models.SMTPInfo;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Component
public class EmailBuilder implements Builder{

    private final MailSubject mailSubject;
    public EmailBuilder(MailSubject mailSubject) {
        this.mailSubject = mailSubject;
    }

    @Override
    public void createTextMail(SMTPInfo smtpInfo, String to, String subject, String text) {
        try {

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(smtpInfo.getEmail());
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            mailSubject.getObserverValue(smtpInfo.getEmail()).send(message);

        }
        catch (MailException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void createMailWithAttachment(SMTPInfo smtpInfo, String to, String subject, String text, String pathToAttachment) {
        try {
            MimeMessage message = mailSubject.getObserverValue(smtpInfo.getEmail()).createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(smtpInfo.getEmail());
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);
            FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
            helper.addAttachment("no caption now", file);
            mailSubject.getObserverValue(smtpInfo.getEmail()).send(message);
        }
        catch (MessagingException exception) {
            exception.printStackTrace();
        }
    }
}
