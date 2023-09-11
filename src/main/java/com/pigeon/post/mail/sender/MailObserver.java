package com.pigeon.post.mail.sender;

import org.springframework.mail.javamail.JavaMailSender;

public interface MailObserver {
    public void registerObserver(String email,JavaMailSender mailSender);
    public void removeObserver(String email);
    public JavaMailSender getObserverValue(String email);
}
