package com.pigeon.post.Services;

import com.pigeon.post.models.Client;
import com.pigeon.post.models.IMAPInfo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IMAPService {
    Flux<IMAPInfo> listAllIMap();
    Mono<IMAPInfo> getIMAPtById(String id);
    Boolean isExistIMAPtByEmail(String email);
    Mono<IMAPInfo> createNewIMAP(IMAPInfo imapInfo,String clientId);
    Mono<IMAPInfo> updateIMAP(String id,IMAPInfo imapInfo);
    Mono<IMAPInfo> patchIMAP(String id,IMAPInfo imapInfo);
}
