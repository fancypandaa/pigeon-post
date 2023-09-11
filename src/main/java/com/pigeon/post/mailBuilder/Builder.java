package com.pigeon.post.mailBuilder;

import com.pigeon.post.models.MailMessage;
import com.pigeon.post.models.SMTPInfo;

public interface Builder {
    void createTextMail(SMTPInfo smtpInfo, MailMessage mailMessage);
    void createMailWithAttachment(SMTPInfo smtpInfo,MailMessage mailMessage);
}
