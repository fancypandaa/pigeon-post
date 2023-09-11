package com.pigeon.post.controllers;

import com.pigeon.post.Services.SMTPService;
import com.pigeon.post.models.SMTPInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
public class SMTPController {
    private final SMTPService smtpService;

    public SMTPController(SMTPService smtpService) {
        this.smtpService = smtpService;
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/v1/smtp")
    Flux<SMTPInfo> SMTPList(){
        return smtpService.listAllSMTP();
    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/v1/smtp/{id}")
    Mono<SMTPInfo> SMTPById(@PathVariable String id){
        return smtpService.getSMTPById(id);
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/v1/smtp/{clientId}")
    Mono<SMTPInfo> createNewSMTP(@PathVariable String clientId,@RequestBody SMTPInfo smtpInfo){
        return smtpService.createNewSMTPInfo(smtpInfo,clientId);
    }
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/api/v1/smtp/{id}")
    Mono<SMTPInfo> updateSMTP(@PathVariable String id,@RequestBody SMTPInfo smtpInfoPublisher){
        return smtpService.updateSMTPInfo(id,smtpInfoPublisher);
    }
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("api/v1/smtp/{id}")
    Mono<SMTPInfo> patchSMTP(@PathVariable String id,@RequestBody SMTPInfo smtpInfoPublisher){
        return smtpService.patchSMTP(id,smtpInfoPublisher);
    }
}
