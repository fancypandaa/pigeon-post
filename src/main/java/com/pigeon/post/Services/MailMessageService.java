package com.pigeon.post.Services;

import com.pigeon.post.models._MailMessage;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MailMessageService {
    Flux<_MailMessage> listAllMailMsg();
    Mono<_MailMessage> getMailById(String id);
    Mono<_MailMessage> createNewMail(_MailMessage mailMessage);
    Mono<_MailMessage> updateMailMsg(String id, _MailMessage mailMessage);
    Mono<_MailMessage> patchMailMsg(String id, _MailMessage mailMessage);
    Mono<Void> removeMailMsg(String id);
}
