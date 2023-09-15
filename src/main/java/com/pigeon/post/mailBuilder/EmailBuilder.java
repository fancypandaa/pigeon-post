package com.pigeon.post.mailBuilder;

import com.pigeon.post.Services.MailMessageService;
import com.pigeon.post.mail.sender.MailSubject;
import com.pigeon.post.models.RecipientType;
import com.pigeon.post.models._MailMessage;
import com.pigeon.post.models.SMTPInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.data.util.Pair;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import java.io.*;
import java.util.*;

@Component
@Slf4j
public class EmailBuilder implements Builder{
    private final int MAX_EMAIL=5;
    private final MailMessageService mailMessageService;
    private final MailSubject mailSubject;

    public EmailBuilder(MailMessageService mailMessageService, MailSubject mailSubject) {
        this.mailMessageService = mailMessageService;
        this.mailSubject = mailSubject;
    }
    @Override
    public void createTextMail(SMTPInfo smtpInfo, _MailMessage mailMessage) {
        try {
            mailSubject.getObserverValue(smtpInfo.getEmail()).createMimeMessage();
            MimeMessagePreparator preparator = new MimeMessagePreparator() {
                public void prepare(MimeMessage message) throws Exception {
                    message.setFrom(new InternetAddress(smtpInfo.getEmail()));
                    message.setSubject(mailMessage.getSubject());
                    message.setText(mailMessage.getMessage());
                    message.setRecipient(Message.RecipientType.TO,
                            new InternetAddress(mailMessage.getTO().iterator().next()));
                    if(mailMessage.getTO().size() > 1){
                        for (String mail : mailMessage.getTO()) {
                            message.addRecipients(Message.RecipientType.TO,mail);
                        }
                    }
                    if(mailMessage.getCC().size() > 1){
                        for (String mail : mailMessage.getCC()) {
                            message.addRecipients(Message.RecipientType.CC,mail);
                        }
                    }
                    if(mailMessage.getBCC().size() > 1){
                        for (String mail : mailMessage.getBCC()) {
                            message.addRecipients(Message.RecipientType.BCC,mail);
                        }
                    }
                    if(mailMessage.getReplyTo().size()> 0){
                        InternetAddress[] internetAddresses = {(InternetAddress) mailMessage.getReplyTo()};
                        message.setReplyTo(internetAddresses);
                    }
                    if(mailMessage.getForwardTo().size()> 0){
                        InternetAddress[] internetAddresses = {(InternetAddress) mailMessage.getForwardTo()};
                        message.setReplyTo(internetAddresses);
                    }
                    message.saveChanges();
                    log.info("MSG_ID "+message.getMessageID()+ "  DATE "+ message.getSentDate());
                    mailMessage.setMessageId(message.getMessageID());
                    mailMessage.setDate(message.getSentDate().toString());
                    mailMessage.setContentType(message.getContentType());
                    mailMessageService.createNewMail(mailMessage).block();

                }

            };

            mailSubject.getObserverValue(smtpInfo.getEmail()).send(preparator);
        }
        catch (MailException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void createMailWithAttachment(SMTPInfo smtpInfo,_MailMessage mailMessage) {
        try {
            MimeMessage message = mailSubject.getObserverValue(smtpInfo.getEmail()).createMimeMessage();
            message.setRecipient(Message.RecipientType.TO,
                    new InternetAddress(mailMessage.getTO().iterator().next()));
            message.setFrom(new InternetAddress(smtpInfo.getEmail()));
            if(mailMessage.getTO().size() > 1){
                for (String mail : mailMessage.getTO()) {
                    message.addRecipients(Message.RecipientType.TO,mail);
                }
            }
            if(mailMessage.getCC().size() > 1){
                for (String mail : mailMessage.getCC()) {
                    message.addRecipients(Message.RecipientType.CC,mail);
                }
            }
            if(mailMessage.getBCC().size() > 1){
                for (String mail : mailMessage.getBCC()) {
                    message.addRecipients(Message.RecipientType.BCC,mail);
                }
            }
            if(mailMessage.getReplyTo().size()> 0){
                InternetAddress[] internetAddresses = {(InternetAddress) mailMessage.getReplyTo()};
                message.setReplyTo(internetAddresses);
            }
            if(mailMessage.getForwardTo().size()> 0){
                InternetAddress[] internetAddresses = {(InternetAddress) mailMessage.getForwardTo()};
                message.setReplyTo(internetAddresses);
            }
//            FileSystemResource file = new FileSystemResource(new File("/home/fancypanda/Downloads/9aceb1c5-d077-4f5b-8e45-65f5d35bd912.JPG"));

            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setSubject(mailMessage.getSubject());
            helper.setText(mailMessage.getMessage());
            ByteArrayDataSource file = new ByteArrayDataSource(mailMessage.getAttachment().getBytes(),mailMessage.getAttachment().getContentType());
            helper.addAttachment(mailMessage.getAttachment().getOriginalFilename(),file );
            message.saveChanges();
            log.info("MSG_ID "+message.getMessageID()+ "  DATE "+ message.getSentDate());
            mailMessage.setMessageId(message.getMessageID());
            mailMessage.setDate(message.getSentDate().toString());
            mailMessage.setContentType(message.getContentType());
            mailMessageService.createNewMail(mailMessage).block();

            mailSubject.getObserverValue(smtpInfo.getEmail()).send(message);
        }
        catch (MessagingException | IOException exception) {
            exception.printStackTrace();
        }
    }
}
