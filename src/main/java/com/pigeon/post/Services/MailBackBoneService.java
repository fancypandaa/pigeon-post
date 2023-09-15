package com.pigeon.post.Services;

import com.pigeon.post.models.MailBackBone;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MailBackBoneService {
    Flux<MailBackBone> listAllMailLogs();
    Mono<MailBackBone> getMailsTree(String id);
    Mono<MailBackBone> getMailByRootId(String id);
    Mono<MailBackBone> createNewMailBB(MailBackBone mailMessage);
    Mono<MailBackBone> updateMailBB(String id,MailBackBone mailMessage);
    Mono<Void> removeMailBB(String id);
}
