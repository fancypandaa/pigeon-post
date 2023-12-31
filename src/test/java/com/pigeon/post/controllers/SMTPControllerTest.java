package com.pigeon.post.controllers;

import com.pigeon.post.Services.SMTPService;
import com.pigeon.post.Services.SMTPServiceImpl;
import com.pigeon.post.mail.sender.MailSubject;
import com.pigeon.post.models.Client;
import com.pigeon.post.models.MailProvider;
import com.pigeon.post.models.SMTPInfo;
import com.pigeon.post.repositories.ClientRepository;
import com.pigeon.post.repositories.SMTPRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

class SMTPControllerTest {
    WebTestClient webTestClient;
    SMTPRepository smtpRepository;
    SMTPController smtpController;
    @Mock
    ClientRepository clientRepository;
    SMTPService smtpService;
    @BeforeEach
    void setUp() {
        smtpRepository = Mockito.mock(SMTPRepository.class);
        clientRepository = Mockito.mock(ClientRepository.class);
        smtpService = new SMTPServiceImpl(smtpRepository,clientRepository);
        smtpController =new SMTPController(smtpService);
        webTestClient =WebTestClient.bindToController(smtpController).build();
    }

    @Test
    void SMTPList() {
        given(smtpRepository.findAll())
                .willReturn(Flux.just(SMTPInfo.builder().email("ml@mail.cm").build(),
                        SMTPInfo.builder().password("dfsf").build(),
                        SMTPInfo.builder().hostProvider(MailProvider.HOTMAIL).build()));
        webTestClient.get()
                .uri("/api/v1/smtp")
                .exchange().expectBodyList(SMTPInfo.class).hasSize(3);
    }

    @Test
    void SMTPById() {
        given(smtpRepository.findById("someid"))
                .willReturn(Mono.just(SMTPInfo.builder().email("ml@mail.cm").build()));
        webTestClient.get()
                .uri("/api/v1/smtp/someid")
                .exchange().expectBodyList(SMTPInfo.class);
    }

    @Test
    void createNewSMTP() {

        given(smtpRepository.save(any(SMTPInfo.class)))
                .willReturn(Mono.just(SMTPInfo.builder().build()));

        Mono<SMTPInfo> smtpInfoMono=Mono.just(SMTPInfo.builder().email("lol@mail.com").build());

        webTestClient.post()
                .uri("/api/v1/smtp/temp")
                .body(smtpInfoMono,SMTPInfo.class)
                .exchange().expectStatus().isCreated();
    }

    @Test
    void patchSMTP() {
        given(smtpRepository.findById(anyString()))
                .willReturn(Mono.just(SMTPInfo.builder().build()));
        given(smtpRepository.save(any(SMTPInfo.class)))
                .willReturn(Mono.just(SMTPInfo.builder().build()));
        Mono<SMTPInfo> smtpInfoMono=Mono.just(SMTPInfo.builder().email("lol@mail.com").build());
        webTestClient.put()
                .uri("/api/v1/smtp/temp")
                .body(smtpInfoMono,SMTPInfo.class)
                .exchange().expectStatus().isOk();
    }

    @Test
    void updateSMTP() {
        given(smtpRepository.save(any(SMTPInfo.class)))
                .willReturn(Mono.just(SMTPInfo.builder().build()));
        Mono<SMTPInfo> smtpInfoMono=Mono.just(SMTPInfo.builder().email("lol@mail.com").build());
        webTestClient.put()
                .uri("/api/v1/smtp/temp")
                .body(smtpInfoMono,SMTPInfo.class)
                .exchange().expectStatus().isOk();
    }
}