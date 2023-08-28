package com.pigeon.post.controllers;

import com.pigeon.post.models.Client;
import com.pigeon.post.models.IMAPInfo;
import com.pigeon.post.repositories.IMAPRepository;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class iMAPController {
    private final IMAPRepository imapRepository;

    public iMAPController(IMAPRepository imapRepository) {
        this.imapRepository = imapRepository;
    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/v1/imap")
    Flux<IMAPInfo> iMAPList(){
        return imapRepository.findAll();
    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/v1/imap/{id}")
    Mono<IMAPInfo> iMAPById(@PathVariable String id){
        return imapRepository.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/v1/imap")
    @Transactional
    Mono<IMAPInfo> createNewImap(@RequestBody IMAPInfo imapInfo){
        return imapRepository.save(imapInfo);
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/api/v1/imap/{id}")
    Mono<IMAPInfo> updateIMAP(@PathVariable String id,@RequestBody IMAPInfo imapInfo){
        imapInfo.setId(id);
        return imapRepository.save(imapInfo);
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PatchMapping("/api/v1/imap/{id}")
    Mono<IMAPInfo> patchIMAP(@PathVariable String id,@RequestBody IMAPInfo imapInfoPublisher){
        IMAPInfo imapInfo= imapRepository.findById(id).block();
        if(imapInfo.getEmail() != imapInfoPublisher.getEmail() && imapInfo.getPassword() != imapInfoPublisher.getPassword()){
            imapInfo.setEmail(imapInfoPublisher.getEmail());
            imapRepository.save(imapInfo);
        }
        return Mono.just(imapInfo);
    }
}
