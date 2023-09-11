package com.pigeon.post.Services;

import com.pigeon.post.models.Client;
import com.pigeon.post.models.IMAPInfo;
import com.pigeon.post.repositories.ClientRepository;
import com.pigeon.post.repositories.IMAPRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
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
    public Boolean isExistIMAPtByEmail(String email) {
        IMAPInfo imapInfo = imapRepository.findIMAPInfoByEmail(email).blockFirst();
        if(imapInfo != null){
            return false;
        }
        return true;
    }

    @Override
    public Mono<IMAPInfo> createNewIMAP(IMAPInfo imapInfo,String clientId) {
        if(isExistIMAPtByEmail(imapInfo.getEmail()) != true){
            log.error("The email u selected Already exist");
            return null;
        }
        Client client  = clientRepository.findById(clientId).block();
        if(client.getId() == null) return Mono.empty();
        IMAPInfo imapInfo1 = imapRepository.save(imapInfo).block();
        client.addiMAPs(imapInfo1);
        clientRepository.save(client).block();

        return Mono.just(imapInfo1);
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
        if(imapInfo.getUsage() != imapInfoPublisher.getUsage() && imapInfoPublisher.getUsage() != null){
            imapInfo.setUsage(imapInfoPublisher.getUsage());
        }
        if(imapInfo.getStorage() != imapInfoPublisher.getStorage() && imapInfoPublisher.getStorage() != null){
            imapInfo.setStorage(imapInfoPublisher.getStorage());
        }
        if(imapInfo.getStatus() != imapInfoPublisher.getStatus()&& imapInfoPublisher.getStatus() != null){
            imapInfo.setStatus(imapInfoPublisher.getStatus());
        }
        imapRepository.save(imapInfo).block();
        return Mono.just(imapInfo);
    }


}
