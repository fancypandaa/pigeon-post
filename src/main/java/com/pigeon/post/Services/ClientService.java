package com.pigeon.post.Services;

import com.pigeon.post.models.Client;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClientService {
    Flux<Client> listAllClients();
    Mono<Client> getClientById(String id);
    Mono<Void> createNewClient(Client client);
    Mono<Client> updateClient(String id,Client client);
    Mono<Client> patchClient(String id,Client client);
    Mono<Void> removeClient(String id);

}
