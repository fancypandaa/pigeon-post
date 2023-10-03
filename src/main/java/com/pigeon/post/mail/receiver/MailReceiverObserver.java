package com.pigeon.post.mail.receiver;

import org.springframework.integration.mail.MailReceiver;

public interface MailReceiverObserver {
    public void registerReceiverObserver(String email, MailReceiver imapMailReceiver);
    public void removeReceiverObserver(String email);
    public void idleReceiverObserver(String email);
    public void notifyReceiverObserver(String email);

    public MailReceiver getObserverValue(String email);
}
