package com.pigeon.post.controllers;

import com.pigeon.post.mail.sender.MailSender;
import com.pigeon.post.mail.sender.MailSubject;
import com.pigeon.post.mailBuilder.EmailBuilder;
import com.pigeon.post.models.SMTPInfo;
import com.pigeon.post.repositories.ClientRepository;
import com.pigeon.post.repositories.SMTPRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
public class MailSenderController extends MailSender {
    private final SMTPRepository smtpRepository;
    private final ClientRepository clientRepository;
    private final MailSubject mailSubject;
    private final EmailBuilder emailBuilder;

    public MailSenderController(SMTPRepository smtpRepository, ClientRepository clientRepository, MailSubject mailSubject, EmailBuilder emailBuilder) {
        this.smtpRepository = smtpRepository;
        this.clientRepository = clientRepository;
        this.mailSubject = mailSubject;
        this.emailBuilder = emailBuilder;
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/api/v1/startSmtp/{id}")
    Mono<Void> startSmtp(@PathVariable String id){
        SMTPInfo smtpInfo= smtpRepository.findById(id).block();
        log.error(smtpInfo.getEmail());
        JavaMailSender mailSender= buildMailSender(smtpInfo);
        mailSubject.registerObserver(smtpInfo.getEmail(),mailSender);
        log.error("Mail Sender Publish in Observer");
        return Mono.empty();
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/api/v1/sendMail/{id}")
    Mono<Void> sendMail(@PathVariable String id){
        SMTPInfo smtpInfo= smtpRepository.findById(id).block();
        log.error(smtpInfo.getEmail());
        emailBuilder.createTextMail(smtpInfo,"mlio64565@gmail.com","loltest","test First Enail");
        return Mono.empty();
    }
}
