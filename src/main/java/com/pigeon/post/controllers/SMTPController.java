package com.pigeon.post.controllers;

import com.pigeon.post.models.Client;
import com.pigeon.post.models.SMTPInfo;
import com.pigeon.post.repositories.ClientRepository;
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
    private final ClientRepository clientRepository;

    public SMTPController(SMTPRepository smtpRepository, ClientRepository clientRepository) {
        this.smtpRepository = smtpRepository;
        this.clientRepository = clientRepository;
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
    @PostMapping("/api/v1/smtp/{clientId}")
    Mono<SMTPInfo> createNewSMTP(@PathVariable String clientId,@RequestBody SMTPInfo smtpInfo){
        Client client  = clientRepository.findById(clientId).block();
        if(client.getId() == null) return Mono.empty();
        else{
            client.addsMTP(smtpInfo);
            Client client1 =clientRepository.save(client).block();
        }
        return smtpRepository.save(smtpInfo);
    }
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/api/v1/smtp/{id}")
    Mono<SMTPInfo> updateSMTP(@PathVariable String id,@RequestBody SMTPInfo smtpInfoPublisher){
        smtpInfoPublisher.setId(id);
        return smtpRepository.save(smtpInfoPublisher);
    }
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("api/v1/smtp/{id}")
    Mono<SMTPInfo> patchSMTP(@PathVariable String id,@RequestBody SMTPInfo smtpInfoPublisher){
        SMTPInfo smtpInfo= smtpRepository.findById(id).block();
        if(smtpInfo.getEmail() == smtpInfoPublisher.getEmail() && smtpInfo.getPassword() != smtpInfoPublisher.getPassword()){
            smtpInfo.setEmail(smtpInfo.getEmail());
            smtpInfo.setPassword(smtpInfo.getPassword());
        }

        smtpRepository.save(smtpInfo);

        return Mono.just(smtpInfo);
    }
}
