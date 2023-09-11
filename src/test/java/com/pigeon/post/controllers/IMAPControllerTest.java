package com.pigeon.post.controllers;

import com.pigeon.post.Services.IMAPService;
import com.pigeon.post.Services.IMAPServiceImpl;
import com.pigeon.post.models.Client;
import com.pigeon.post.models.IMAPInfo;
import com.pigeon.post.repositories.ClientRepository;
import com.pigeon.post.repositories.IMAPRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

class IMAPControllerTest {

    WebTestClient webTestClient;
    IMAPRepository imapRepository;
    IMAPService imapService;
    IMAPController iMAPController;
    ClientRepository clientRepository;
    @BeforeEach
    void setUp() {
        imapRepository = Mockito.mock(IMAPRepository.class);
        clientRepository = Mockito.mock(ClientRepository.class);
        imapService = new IMAPServiceImpl(imapRepository,clientRepository);
        iMAPController = new IMAPController(imapService);

        webTestClient = WebTestClient.bindToController(iMAPController).build();
    }

    @Test
    void iMAPList() {
        given(imapService.listAllIMap())
                .willReturn(Flux.just(IMAPInfo.builder().email("ml@mail.cm").build(),
                        IMAPInfo.builder().password("dfsf").build(),
                        IMAPInfo.builder().storage("200").build()));
        webTestClient.get()
                .uri("/api/v1/imap")
                .exchange().expectBodyList(IMAPInfo.class).hasSize(3);
    }
    @Test
    void iMAPById() {
        given(imapService.getIMAPtById("someid"))
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
        Mono<Client> clientMono=Mono.just(Client.builder().alias("lol@mail.com").build());

        webTestClient.post()
                .uri("/api/v1/imap/xx")
                .body(imapInfoMono,IMAPInfo.class)
                .exchange().expectStatus().isCreated();
    }

    @Test
    void updateIMAP() {

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