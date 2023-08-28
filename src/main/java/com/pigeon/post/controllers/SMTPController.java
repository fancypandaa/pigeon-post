package com.pigeon.post.controllers;

import com.pigeon.post.models.SMTPInfo;
import com.pigeon.post.repositories.SMTPRepository;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class SMTPController {
    private final SMTPRepository smtpRepository;
    private final PasswordEncoder passwordEncoder;

    public SMTPController(SMTPRepository smtpRepository, PasswordEncoder passwordEncoder) {
        this.smtpRepository = smtpRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/v1/smtp")
    Flux<SMTPInfo> SMTPList(){
        return smtpRepository.findAll();
    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/v1/smtp/{id}")
    Mono<SMTPInfo> SMTPById(@PathVariable String id){
        return smtpRepository.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/v1/smtp")
    Mono<Void> createNewSMTP(@RequestBody SMTPInfo smtpInfo){
        return smtpRepository.save(smtpInfo).then();
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/api/v1/smtp/{id}")
    Mono<SMTPInfo> updateSMTP(@PathVariable String id,@RequestBody SMTPInfo smtpInfoPublisher){
        smtpInfoPublisher.setId(id);
        return smtpRepository.save(smtpInfoPublisher);
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PatchMapping("api/v1/smtp/{id}")
    Mono<SMTPInfo> patchSMTP(@PathVariable String id,@RequestBody SMTPInfo smtpInfoPublisher){
        SMTPInfo smtpInfo= smtpRepository.findById(id).block();
        if(smtpInfo.getEmail() != smtpInfoPublisher.getEmail() && smtpInfo.getPassword() != smtpInfoPublisher.getPassword()){
            smtpInfo.setEmail(smtpInfo.getEmail());
            smtpInfo.setPassword(smtpInfo.getPassword());
            smtpRepository.save(smtpInfo);
        }
        return Mono.just(smtpInfo);
    }
}
