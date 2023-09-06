package com.pigeon.post.Services;

import com.pigeon.post.models.Client;
import com.pigeon.post.models.IMAPInfo;
import com.pigeon.post.repositories.ClientRepository;
import com.pigeon.post.repositories.IMAPRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class IMAPServiceImpl implements IMAPService{
    private final IMAPRepository imapRepository;
    private final ClientRepository clientRepository;

    public IMAPServiceImpl(IMAPRepository imapRepository, ClientRepository clientRepository) {
        this.imapRepository = imapRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public Flux<IMAPInfo> listAllIMap() {
        return imapRepository.findAll();
    }

    @Override
    public Mono<IMAPInfo> getIMAPtById(String id) {
        return imapRepository.findById(id);
    }

    @Override
    public Mono<Boolean> isExistIMAPtByEmail(String email) {
        IMAPInfo imapInfo = imapRepository.findIMAPInfoByEmail(email).block();
        if(imapInfo != null){
            return Mono.just(false);
        }
        return Mono.just(true);
    }

    @Override
    public Mono<IMAPInfo> createNewIMAP(IMAPInfo imapInfo,String clientId) {
        if(isExistIMAPtByEmail(imapInfo.getEmail()).block()== true){
            return null;
        }
        Client client  = clientRepository.findById(clientId).block();
        if(client.getId() == null) return Mono.empty();
        else{
            client.addiMAPs(imapInfo);
            Client client1 =clientRepository.save(client).block();
        }
        return imapRepository.save(imapInfo);
    }

    @Override
    public Mono<IMAPInfo> updateIMAP(String id, IMAPInfo imapInfo) {
        imapInfo.setId(id);
        return imapRepository.save(imapInfo);
    }

    @Override
    public Mono<IMAPInfo> patchIMAP(String id, IMAPInfo imapInfoPublisher) {
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

    @Override
    public Mono<Void> removeIMAP(String id) {
        return imapRepository.deleteById(id).then();
    }
}
