package com.pigeon.post.controllers;

import com.pigeon.post.models.Client;
import com.pigeon.post.models.IMAPInfo;
import com.pigeon.post.repositories.ClientRepository;
import com.pigeon.post.repositories.IMAPRepository;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class iMAPController {
    private final IMAPRepository imapRepository;
    private final ClientRepository clientRepository;

    public iMAPController(IMAPRepository imapRepository, ClientRepository clientRepository) {
        this.imapRepository = imapRepository;
        this.clientRepository = clientRepository;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/v1/imap")
    Flux<IMAPInfo> iMAPList(){
        return imapRepository.findAll();
    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/v1/imap/{id}")
    Mono<IMAPInfo> iMAPById(@PathVariable String id){
        return imapRepository.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/v1/imap/{clientId}")
    @Transactional
    Mono<IMAPInfo> createNewImap(@PathVariable String clientId,@RequestBody IMAPInfo imapInfo){
        Client client  = clientRepository.findById(clientId).block();
        if(client.getId() == null) return Mono.empty();
        else{
            client.addiMAPs(imapInfo);
            Client client1 =clientRepository.save(client).block();
        }
       return imapRepository.save(imapInfo);
    }
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/api/v1/imap/{id}")
    Mono<IMAPInfo> updateIMAP(@PathVariable String id,@RequestBody IMAPInfo imapInfo){
        imapInfo.setId(id);
        return imapRepository.save(imapInfo);
    }
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/api/v1/imap/{id}")
    Mono<IMAPInfo> patchIMAP(@PathVariable String id,@RequestBody IMAPInfo imapInfoPublisher){
        IMAPInfo imapInfo= imapRepository.findById(id).block();

        if(imapInfo.getPassword() != imapInfoPublisher.getPassword() && imapInfo.getEmail() == imapInfoPublisher.getEmail() ){
            imapInfo.setPassword(imapInfoPublisher.getPassword());
        }
        if(imapInfo.getUsage() != imapInfoPublisher.getUsage()){
            imapInfo.setUsage(imapInfoPublisher.getUsage());
        }
        if(imapInfo.getStorage() != imapInfoPublisher.getStorage()){
            imapInfo.setStorage(imapInfoPublisher.getStorage());
        }
        imapRepository.save(imapInfo);
        return Mono.just(imapInfo);
    }
}
