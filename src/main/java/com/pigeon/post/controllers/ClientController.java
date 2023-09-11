package com.pigeon.post.controllers;

import com.pigeon.post.Services.ClientService;
import com.pigeon.post.models.Client;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class ClientController {
    private final ClientService clientService;


    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/v1/clients")
    Flux<Client> clientList(){
      return clientService.listAllClients();
    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/v1/clients/{id}")
    Mono<Client> getClientById(@PathVariable String id){
        return clientService.getClientById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/v1/clients")
    Mono<Client> create(@RequestBody Client clientStream){
        return clientService.createNewClient(clientStream);
    }
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/api/v1/clients/{id}")
    Mono<Client> updateClient(@PathVariable String id,@RequestBody Client clientPublisher){
        return clientService.updateClient(id,clientPublisher);
    }
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/api/v1/clients/{id}")
    Mono<Client> patchClient(@PathVariable String id,@RequestBody Client clientPublisher){
        return clientService.patchClient(id,clientPublisher);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/api/v1/clients/{id}")
    Mono<Void> deleteClient(@PathVariable String id){
        return clientService.removeClient(id);
    }
}
