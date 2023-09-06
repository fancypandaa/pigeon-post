package com.pigeon.post.controllers;

import com.pigeon.post.Services.ClientService;
import com.pigeon.post.Services.ClientServiceImpl;
import com.pigeon.post.models.Client;
import com.pigeon.post.models.IMAPInfo;
import com.pigeon.post.models.PricePackage;
import com.pigeon.post.repositories.ClientRepository;
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

class ClientControllerTest {
    ClientController clientController;
    ClientRepository clientRepository;
    ClientService clientService;
    WebTestClient webTestClient;
    @BeforeEach
    void setUp() {
        clientRepository = Mockito.mock(ClientRepository.class);
        clientService =new ClientServiceImpl(clientRepository);
        clientController = new ClientController(clientService);
        webTestClient= WebTestClient.bindToController(clientController).build();
    }

    @Test
    void clientList() {
        given(clientService.listAllClients())
                .willReturn(Flux.just(Client.builder().alias("ml@mail.cm").build(),
                        Client.builder().businessName("dfsf").build(),
                        Client.builder().businessType("ss").build(),
                        Client.builder().pricePackage(PricePackage.FREE).build(),
                        Client.builder().status("verify").build()));
        webTestClient.get()
                .uri("/api/v1/clients")
                .exchange().expectBodyList(IMAPInfo.class).hasSize(5);
    }

    @Test
    void getClientById() {
        given(clientService.getClientById("someid"))
                .willReturn(Mono.just(Client.builder().alias("ml@mail.cm").build()));
        webTestClient.get()
                .uri("/api/v1/clients/someid")
                .exchange().expectBodyList(Client.class);
    }

    @Test
    void create() {
        given(clientService.createNewClient(any(Client.class)))
                .willReturn(Mono.just(Client.builder().alias("lol@mail.com").build()));

        Mono<Client> clientMono=Mono.just(Client.builder().alias("lol@mail.com").build());

        webTestClient.post()
                .uri("/api/v1/clients")
                .body(clientMono,Client.class)
                .exchange().expectStatus().isCreated();
    }



    @Test
    void patchClient() {
        given(clientService.getClientById(anyString()))
                .willReturn(Mono.just(Client.builder().build()));

        given(clientService.createNewClient(any(Client.class)))
                .willReturn(Mono.just(Client.builder().build()));

        Mono<Client> clientMono=Mono.just(Client.builder().alias("lol@mail.com").build());

        webTestClient.patch()
                .uri("/api/v1/clients/xxx")
                .body(clientMono,Client.class)
                .exchange().expectStatus().isOk();
    }


}