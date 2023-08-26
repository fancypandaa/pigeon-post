package com.pigeon.post.controllers;

import com.pigeon.post.models.Client;
import com.pigeon.post.models.IMAPInfo;
import com.pigeon.post.repositories.IMAPRepository;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class iMAPController {
    private final IMAPRepository imapRepository;
    private final PasswordEncoder passwordEncoder;

    public iMAPController(IMAPRepository imapRepository, PasswordEncoder passwordEncoder) {
        this.imapRepository = imapRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/v1/imap")
    Flux<IMAPInfo> iMAPList(){
        return imapRepository.findAll();
    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/v1/imap/{id}")
    Mono<IMAPInfo> iMAPById(@PathVariable String Id){
        return imapRepository.findById(Id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("api/v1/imap")
    Mono<Void> createNewImap(@RequestBody Publisher<IMAPInfo> imapInfoPublisher){
        return imapRepository.saveAll(imapInfoPublisher).then();
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("api/v1/imap/{id}")
    Mono<IMAPInfo> updateIMAP(@PathVariable String id,@RequestBody IMAPInfo imapInfoPublisher){
        imapInfoPublisher.setId(id);
        return imapRepository.save(imapInfoPublisher);
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PatchMapping("api/v1/imap/{id}")
    Mono<IMAPInfo> patchIMAP(@PathVariable String id,@RequestBody IMAPInfo imapInfoPublisher){
        IMAPInfo imapInfo= imapRepository.findById(id).block();
        if(imapInfo.getEmail() != imapInfoPublisher.getEmail() || imapInfo.getPassword() != imapInfoPublisher.getPassword()){
            imapRepository.save(imapInfo);
        }
        return Mono.just(imapInfo);
    }
}
