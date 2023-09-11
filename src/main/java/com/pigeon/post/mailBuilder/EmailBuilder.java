package com.pigeon.post.mailBuilder;

import com.pigeon.post.mail.sender.MailSubject;
import com.pigeon.post.models.MailMessage;
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
    public void createTextMail(SMTPInfo smtpInfo, MailMessage mailMessage) {
        try {

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(smtpInfo.getEmail());
            message.setTo(mailMessage.getTo());
            message.setSubject(mailMessage.getSubject());
            message.setText(mailMessage.getMessage());
            if(mailMessage.getReplyTo() != null){
                message.setReplyTo(mailMessage.getReplyTo());
            }
            if(mailMessage.getForwardTo() != null){
                message.setBcc(mailMessage.getForwardTo());
                message.setCc(mailMessage.getForwardTo());
            }
            mailSubject.getObserverValue(smtpInfo.getEmail()).send(message);

        }
        catch (MailException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void createMailWithAttachment(SMTPInfo smtpInfo,MailMessage mailMessage) {
        try {
            MimeMessage message = mailSubject.getObserverValue(smtpInfo.getEmail()).createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(smtpInfo.getEmail());
            helper.setTo(mailMessage.getTo());
            helper.setSubject(mailMessage.getSubject());
            helper.setText(mailMessage.getMessage());
            FileSystemResource file = new FileSystemResource(new File(mailMessage.getAttachmentUrl()));
            helper.addAttachment("no caption now", file);
            if(mailMessage.getReplyTo() != null){
                helper.setReplyTo(mailMessage.getReplyTo());
            }
            if(mailMessage.getForwardTo() != null){
                helper.setBcc(mailMessage.getForwardTo());
                helper.setCc(mailMessage.getForwardTo());
            }
            mailSubject.getObserverValue(smtpInfo.getEmail()).send(message);
        }
        catch (MessagingException exception) {
            exception.printStackTrace();
        }
    }
}
