package com.pigeon.post.mail.sender;
import com.pigeon.post.models.SMTPInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.*;
@Component
@Slf4j
public class MailSubject implements MailObserver{
    private HashMap<String,JavaMailSender> mailSenders;
    private SMTPInfo smtpInfo;
    public MailSubject(List<JavaMailSender> mailSenders) {
        this.mailSenders = new HashMap<String,JavaMailSender>();
    }

    @Override
    public void registerObserver(String email,JavaMailSender mailSender) {
        mailSenders.put(email,mailSender);
        log.debug("registered Observers: ",mailSenders.size());
    }

    @Override
    public void removeObserver(String email) {
        mailSenders.remove(email);
    }

    @Override
    public JavaMailSender getObserverValue(String email) {
        return mailSenders.get(email);
    }
}
