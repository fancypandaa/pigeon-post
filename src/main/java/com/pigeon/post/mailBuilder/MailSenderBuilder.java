package com.pigeon.post.mailBuilder;

import com.pigeon.post.models._MailMessage;
import com.pigeon.post.models.SMTPInfo;

public interface MailSenderBuilder {
    void createTextMail(SMTPInfo smtpInfo, _MailMessage mailMessage);
    void createMailWithAttachment(SMTPInfo smtpInfo, _MailMessage mailMessage);
}
