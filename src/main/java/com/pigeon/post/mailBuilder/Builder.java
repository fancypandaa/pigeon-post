package com.pigeon.post.mailBuilder;

import com.pigeon.post.models.SMTPInfo;

public interface Builder {
    void createTextMail(SMTPInfo smtpInfo, String to, String subject, String text);
    void createMailWithAttachment(SMTPInfo smtpInfo,String to, String subject, String text,String pathToAttachment);

}
