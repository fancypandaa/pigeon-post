package com.pigeon.post.controllers;

import com.pigeon.post.models.Client;
import com.pigeon.post.repositories.ClientRepository;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class ClientController {
    private final ClientRepository clientRepository;

    public ClientController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/v1/clients")
    Flux<Client> clientList(){
      return clientRepository.findAll();
    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/v1/clients/{id}")
    Mono<Client> getClientById(@PathVariable String Id){
        return clientRepository.findById(Id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("api/v1/clients")
    Mono<Void> newClient(@RequestBody Publisher<Client> clientPublisher){
        return clientRepository.saveAll(clientPublisher).then();
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("api/v1/clients/{id}")
    Mono<Client> updateClient(@PathVariable String id,@RequestBody Client clientPublisher){
        clientPublisher.setId(id);
        return clientRepository.save(clientPublisher);
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PatchMapping("api/v1/clients/{id}")
    Mono<Client> patchClient(@PathVariable String id,@RequestBody Client clientPublisher){
        Client client= clientRepository.findById(id).block();
        if(client.getAlias()!= clientPublisher.getAlias()|| client.getStatus() != clientPublisher.getStatus()
        || client.getPricePackage()!= clientPublisher.getPricePackage()){
            clientRepository.save(client);
        }
        return Mono.just(client);
    }
}
