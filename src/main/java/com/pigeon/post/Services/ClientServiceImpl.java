package com.pigeon.post.Services;

import com.pigeon.post.models.Client;
import com.pigeon.post.repositories.ClientRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Service
public class ClientServiceImpl implements ClientService{
    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Flux<Client> listAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public Mono<Client> getClientById(String id) {
        return clientRepository.findById(id);
    }

    @Override
    public Mono<Client> createNewClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Mono<Client> updateClient(String id, Client client) {
        client.setId(id);
        return clientRepository.save(client);
    }

    @Override
    public Mono<Client> patchClient(String id, Client client) {
        Client clientI= clientRepository.findById(id).block();
        if(clientI.getAlias()!=client.getAlias()){
            clientI.setAlias(client.getAlias());
        }
        if(clientI.getPricePackage()!=client.getPricePackage()){
            clientI.setPricePackage(client.getPricePackage());
        }
        clientRepository.save(clientI);

        return Mono.just(clientI);
    }

    @Override
    public Mono<Void> removeClient(String id) {
        return clientRepository.deleteById(id).then();
    }
}
