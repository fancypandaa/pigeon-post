package com.pigeon.post.mailBuilder;

import javax.mail.internet.MimeMessage;

public interface MailReceiverBuilder {
    void handleReceivedMail(MimeMessage message);
}
