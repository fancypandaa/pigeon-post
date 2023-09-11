package com.pigeon.post.Services;

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

class IMAPServiceImplTest {

    WebTestClient webTestClient;
    IMAPRepository imapRepository;
    IMAPService imapService;
    ClientRepository clientRepository;
    @BeforeEach
    void setUp() {
        imapRepository = Mockito.mock(IMAPRepository.class);
        clientRepository = Mockito.mock(ClientRepository.class);
        imapService = new IMAPServiceImpl(imapRepository,clientRepository);
    }


    @Test
    void listAllIMap() {

        given(imapRepository.findAll())
                .willReturn(Flux.just(IMAPInfo.builder().email("ml@mail.cm").build(),
                        IMAPInfo.builder().password("dfsf").build(),
                        IMAPInfo.builder().storage("200").build()));
    }

    @Test
    void getIMAPtById() {

        given(imapRepository.findById("someid"))
                .willReturn(Mono.just(IMAPInfo.builder().email("ml@mail.cm").build()));
    }


    @Test
    void createNewIMAP() {

        given(clientRepository.save(any(Client.class)))
                .willReturn(Mono.just(Client.builder().build()));

        given(imapRepository.save(any(IMAPInfo.class)))
                .willReturn(Mono.just(IMAPInfo.builder().build()));

        Mono<IMAPInfo> imapInfoMono=Mono.just(IMAPInfo.builder().email("lol@mail.com").build());
    }

    @Test
    void updateIMAP() {

        given(imapRepository.save(any(IMAPInfo.class)))
                .willReturn(Mono.just(IMAPInfo.builder().build()));
        Mono<IMAPInfo> imapInfoMono=Mono.just(IMAPInfo.builder().email("lol@mail.com").build());
    }

    @Test
    void patchIMAP() {

        given(imapRepository.findById(anyString()))
                .willReturn(Mono.just(IMAPInfo.builder().build()));
        given(imapRepository.save(any(IMAPInfo.class)))
                .willReturn(Mono.just(IMAPInfo.builder().build()));
    }
}