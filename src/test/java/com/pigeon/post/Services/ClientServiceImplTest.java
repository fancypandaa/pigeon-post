package com.pigeon.post.Services;

import com.pigeon.post.models.Client;
import com.pigeon.post.models.PricePackage;
import com.pigeon.post.repositories.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

class ClientServiceImplTest {
    ClientRepository clientRepository;
    ClientService clientService;
    WebTestClient webTestClient;
    @BeforeEach
    void setUp() {
        clientRepository = Mockito.mock(ClientRepository.class);
        clientService =new ClientServiceImpl(clientRepository);
        webTestClient= WebTestClient.bindToController(clientService).build();
    }

    @Test
    void listAllClients() {
        given(clientRepository.findAll())
                .willReturn(Flux.just(Client.builder().alias("ml@mail.cm").build(),
                        Client.builder().businessName("dfsf").build(),
                        Client.builder().businessType("ss").build(),
                        Client.builder().pricePackage(PricePackage.FREE).build(),
                        Client.builder().status("verify").build()));
    }

    @Test
    void getClientById() {

        given(clientRepository.findById("someid"))
                .willReturn(Mono.just(Client.builder().alias("ml@mail.cm").build()));
    }

    @Test
    void createNewClient() {

        given(clientRepository.save(any(Client.class)))
                .willReturn(Mono.just(Client.builder().alias("lol@mail.com").build()));

        Mono<Client> clientMono=Mono.just(Client.builder().alias("lol@mail.com").build());

    }

    @Test
    void updateClient() {

        given(clientRepository.save(any(Client.class)))
                .willReturn(Mono.just(Client.builder().build()));
        Mono<Client> client=Mono.just(Client.builder().alias("lol@mail.com").build());

    }

    @Test
    void patchClient() {

        given(clientRepository.findById(anyString()))
                .willReturn(Mono.just(Client.builder().build()));

        given(clientRepository.save(any(Client.class)))
                .willReturn(Mono.just(Client.builder().build()));

        Mono<Client> clientMono=Mono.just(Client.builder().alias("lol@mail.com").build());


    }


}