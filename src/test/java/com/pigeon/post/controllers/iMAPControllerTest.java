package com.pigeon.post.controllers;

import com.pigeon.post.models.Client;
import com.pigeon.post.models.IMAPInfo;
import com.pigeon.post.models.MailProvider;
import com.pigeon.post.models.SMTPInfo;
import com.pigeon.post.repositories.ClientRepository;
import com.pigeon.post.repositories.IMAPRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

class iMAPControllerTest {

    WebTestClient webTestClient;
    IMAPRepository imapRepository;
    iMAPController iMAPController;
    ClientRepository clientRepository;
    @BeforeEach
    void setUp() {
        imapRepository = Mockito.mock(IMAPRepository.class);
        clientRepository = Mockito.mock(ClientRepository.class);
        iMAPController =new iMAPController(imapRepository,clientRepository);
        webTestClient = WebTestClient.bindToController(iMAPController).build();
    }

    @Test
    void iMAPList() {
        given(imapRepository.findAll())
                .willReturn(Flux.just(IMAPInfo.builder().email("ml@mail.cm").build(),
                        IMAPInfo.builder().password("dfsf").build(),
                        IMAPInfo.builder().Storage("200").build()));
        webTestClient.get()
                .uri("/api/v1/imap")
                .exchange().expectBodyList(IMAPInfo.class).hasSize(3);
    }
    @Test
    void iMAPById() {
        given(imapRepository.findById("someid"))
            .willReturn(Mono.just(IMAPInfo.builder().email("ml@mail.cm").build()));
        webTestClient.get()
                .uri("/api/v1/imap/someid")
                .exchange().expectBodyList(IMAPInfo.class);
    }

    @Test
    void createNewImap() {
        given(clientRepository.findById(anyString()))
                .willReturn(Mono.just(Client.builder().build()));

        given(imapRepository.save(any(IMAPInfo.class)))
                .willReturn(Mono.just(IMAPInfo.builder().build()));

        Mono<IMAPInfo> imapInfoMono=Mono.just(IMAPInfo.builder().email("lol@mail.com").build());

        webTestClient.post()
                .uri("/api/v1/imap/temp")
                .body(imapInfoMono,IMAPInfo.class)
                .exchange().expectStatus().isCreated();
    }

    @Test
    void updateIMAP() {
        given(imapRepository.save(any(IMAPInfo.class)))
                .willReturn(Mono.just(IMAPInfo.builder().build()));
        Mono<IMAPInfo> imapInfoMono=Mono.just(IMAPInfo.builder().email("lol@mail.com").build());
        webTestClient.put()
                .uri("/api/v1/imap/temp")
                .body(imapInfoMono,IMAPInfo.class)
                .exchange().expectStatus().isOk();
    }

    @Test
    void patchIMAP() {
        given(imapRepository.findById(anyString()))
                .willReturn(Mono.just(IMAPInfo.builder().build()));
        given(imapRepository.save(any(IMAPInfo.class)))
                .willReturn(Mono.just(IMAPInfo.builder().build()));
        Mono<IMAPInfo> imapInfoMono=Mono.just(IMAPInfo.builder().email("lol@mail.com").build());
        webTestClient.put()
                .uri("/api/v1/imap/temp")
                .body(imapInfoMono,IMAPInfo.class)
                .exchange().expectStatus().isOk();
    }
}