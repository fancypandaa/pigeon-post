package com.pigeon.post.Services;

import com.pigeon.post.models.IMAPInfo;
import com.pigeon.post.models.SMTPInfo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SMTPService {
    Flux<SMTPInfo> listAllSMTP();
    Mono<SMTPInfo> getSMTPById(String id);
    Boolean isExistSMTPByEmail(String email);
    Mono<SMTPInfo> createNewSMTPInfo(SMTPInfo smtpInfo,String clientId);
    Mono<SMTPInfo> updateSMTPInfo(String id,SMTPInfo smtpInfo);
    Mono<SMTPInfo> patchSMTP(String id,SMTPInfo smtpInfo);
//    Mono<Void> removeSMTP(String id);
}
