package com.pigeon.post.Services;

import com.pigeon.post.models.Client;
import com.pigeon.post.models.MailMessage;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MailMessageService {
    Flux<MailMessage> listAllMailMsg();
    Mono<MailMessage> getMailById(String id);
    Mono<MailMessage> createNewMail(MailMessage mailMessage);
    Mono<MailMessage> updateMailMsg(String id,MailMessage mailMessage);
    Mono<MailMessage> patchMailMsg(String id,MailMessage mailMessage);
    Mono<Void> removeMailMsg(String id);
}
