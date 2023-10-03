package com.pigeon.post.mail.receiver;

import com.pigeon.post.models.IMAPInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.mail.MailReceiver;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
@Component
@Slf4j
public class MailReceiverSubject implements MailReceiverObserver {
    private HashMap<String,MailReceiver> mailReceivers;
    private IMAPInfo imapInfo;
    public MailReceiverSubject(List<MailReceiver> mailReceivers) {
        this.mailReceivers = new HashMap<String,MailReceiver>();
    }

    @Override
    public void registerReceiverObserver(String email, MailReceiver imapMailReceiver) {
        mailReceivers.put(email,imapMailReceiver);
        log.debug("registered Observers: ",mailReceivers.size());
    }

    @Override
    public void removeReceiverObserver(String email) {
    mailReceivers.remove(email);
    log.debug("remove Observers: ",mailReceivers.size());
    }

    @Override
    public void idleReceiverObserver(String email) {
    //
    }

    @Override
    public void notifyReceiverObserver(String email) {
        //
    }

    @Override
    public MailReceiver getObserverValue(String email) {
        return null;
    }
}
