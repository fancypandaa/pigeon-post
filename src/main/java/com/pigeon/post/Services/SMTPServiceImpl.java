package com.pigeon.post.Services;

import com.pigeon.post.models.Client;
import com.pigeon.post.models.SMTPInfo;
import com.pigeon.post.repositories.ClientRepository;
import com.pigeon.post.repositories.SMTPRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Service
@Slf4j
public class SMTPServiceImpl implements SMTPService{

    private final SMTPRepository smtpRepository;
    private final ClientRepository clientRepository;

    public SMTPServiceImpl(SMTPRepository smtpRepository, ClientRepository clientRepository) {
        this.smtpRepository = smtpRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public Flux<SMTPInfo> listAllSMTP() {
        return smtpRepository.findAll();
    }

    @Override
    public Mono<SMTPInfo> getSMTPById(String id) {
        return smtpRepository.findById(id);
    }

    @Override
    public Boolean isExistSMTPByEmail(String email) {
        SMTPInfo smtpInfo = smtpRepository.findSMTPInfoByEmail(email).blockFirst();
        if(smtpInfo != null){
            return false;
        }
        return true;
    }

    @Override
    public Mono<SMTPInfo> createNewSMTPInfo(SMTPInfo smtpInfo, String clientId) {
        if(isExistSMTPByEmail(smtpInfo.getEmail()) != true){
            log.error("The email u selected Already exist");
            return null;
        }
        Client client  = clientRepository.findById(clientId).block();
        if(client.getId() == null) return Mono.empty();
        SMTPInfo smtpInfo1 =smtpRepository.save(smtpInfo).block();
        client.addsMTP(smtpInfo1);
        clientRepository.save(client).block();
        return Mono.just(smtpInfo1);
    }

    @Override
    public Mono<SMTPInfo> updateSMTPInfo(String id, SMTPInfo smtpInfoPublisher) {
        smtpInfoPublisher.setId(id);
        return smtpRepository.save(smtpInfoPublisher);
    }

    @Override
    public Mono<SMTPInfo> patchSMTP(String id, SMTPInfo smtpInfoPublisher) {
        SMTPInfo smtpInfo= smtpRepository.findById(id).block();

        if((smtpInfo.getPassword() != smtpInfoPublisher.getPassword()
                && smtpInfoPublisher.getPassword() != null)){
            System.out.println("LOLO SMTP WAS UPDATED!!");
            smtpInfo.setEmail(smtpInfoPublisher.getEmail());
            smtpInfo.setPassword(smtpInfoPublisher.getPassword());
        }

        smtpRepository.save(smtpInfo).block();

        return Mono.just(smtpInfo);
    }
//
//    @Override
//    public Mono<Void> removeSMTP(String id) {
//        return smtpRepository.deleteById(id);
//    }
}
