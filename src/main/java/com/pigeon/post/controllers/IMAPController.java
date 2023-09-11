package com.pigeon.post.controllers;

import com.pigeon.post.Services.IMAPService;
import com.pigeon.post.models.IMAPInfo;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class IMAPController {
    private final IMAPService imapService;

    public IMAPController(IMAPService imapService) {
        this.imapService = imapService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/v1/imap")
    Flux<IMAPInfo> iMAPList(){
        return imapService.listAllIMap();
    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/v1/imap/{id}")
    Mono<IMAPInfo> iMAPById(@PathVariable String id){
        return imapService.getIMAPtById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/v1/imap/{clientId}")
    @Transactional
    Mono<IMAPInfo> createNewImap(@PathVariable String clientId,@RequestBody IMAPInfo imapInfo){
        return imapService.createNewIMAP(imapInfo,clientId);
    }
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/api/v1/imap/{id}")
    Mono<IMAPInfo> updateIMAP(@PathVariable String id,@RequestBody IMAPInfo imapInfo){
        return imapService.updateIMAP(id,imapInfo);
    }
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/api/v1/imap/{id}")
    Mono<IMAPInfo> patchIMAP(@PathVariable String id,@RequestBody IMAPInfo imapInfoPublisher){
        return imapService.patchIMAP(id,imapInfoPublisher);
    }
//    @ResponseStatus(HttpStatus.OK)
//    @DeleteMapping("/api/v1/clients/{id}")
//    Mono<Void> deleteIMAP(@PathVariable String id){
//        return imapService.removeIMAP(id);
//    }
}
