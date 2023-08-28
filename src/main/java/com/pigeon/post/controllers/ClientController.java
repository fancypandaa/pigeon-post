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
    Mono<Client> getClientById(@PathVariable String id){
        return clientRepository.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/v1/clients")
    Mono<Void> create(@RequestBody Client clientStream){
        return clientRepository.save(clientStream).then();
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/api/v1/clients/{id}")
    Mono<Client> updateClient(@PathVariable String id,@RequestBody Client clientPublisher){
        clientPublisher.setId(id);
        return clientRepository.save(clientPublisher);
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PatchMapping("/api/v1/clients/{id}")
    Mono<Client> patchClient(@PathVariable String id,@RequestBody Client clientPublisher){
        Client client= clientRepository.findById(id).block();
        if(!client.getAlias().equals(clientPublisher.getAlias())){
            client.setAlias(client.getAlias());
        }
        if(!client.getPricePackage().equals(clientPublisher.getPricePackage())){
            client.setPricePackage(clientPublisher.getPricePackage());
        }
        clientRepository.save(client);

        return Mono.just(client);
    }
    @ResponseStatus(HttpStatus.GONE)
    @DeleteMapping("/api/v1/clients/{id}")
    Mono<Void> deleteClient(@PathVariable String id){
        return clientRepository.deleteById(id).then();
    }
}
