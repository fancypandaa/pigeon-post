package com.pigeon.post.controllers;

import com.pigeon.post.Services.IMAPService;
import com.pigeon.post.mail.receiver.MailReceiverConfiguration;
import com.pigeon.post.mail.receiver.MailReceiverSubject;
import com.pigeon.post.mailBuilder.MailReceiverService;
import com.pigeon.post.models.IMAPInfo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
public class MailReceiverController {
    private final MailReceiverService imapService;
    private final MailReceiverSubject mailReceiverSubject;
    private final IMAPService imapServiceI;


    public MailReceiverController(MailReceiverService imapService, MailReceiverSubject mailReceiverSubject, IMAPService imapServiceI) {
        this.imapService = imapService;
        this.mailReceiverSubject = mailReceiverSubject;
        this.imapServiceI = imapServiceI;
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/api/v1/startIMAP/{id}")
    Mono<Void> startIMAP(@PathVariable String id){
        IMAPInfo imapInfo= this.imapServiceI.getIMAPtById(id).block();
        log.error(imapInfo.getEmail());
//        MailReceiverConfiguration mailReceiverConfiguration= new MailReceiverConfiguration(imapService, imapService);
//        mailReceiverSubject.registerReceiverObserver(imapInfo.getEmail(),mailReceiver);
        log.info("Mail receiver Publish in Observer");
        return Mono.empty();
    }
}
