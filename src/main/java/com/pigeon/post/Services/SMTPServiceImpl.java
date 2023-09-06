package com.pigeon.post.Services;

import com.pigeon.post.models.Client;
import com.pigeon.post.models.IMAPInfo;
import com.pigeon.post.models.SMTPInfo;
import com.pigeon.post.repositories.ClientRepository;
import com.pigeon.post.repositories.SMTPRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Service
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
    public Mono<Boolean> isExistSMTPByEmail(String email) {
        SMTPInfo smtpInfo = smtpRepository.findSMTPInfoByEmail(email);
        if(smtpInfo != null){
            return Mono.just(false);
        }
        return Mono.just(true);    }

    @Override
    public Mono<SMTPInfo> createNewSMTPInfo(SMTPInfo smtpInfo, String clientId) {
        if(isExistSMTPByEmail(smtpInfo.getEmail()).block() == true){
          return null;
        }
        Client client  = clientRepository.findById(clientId).block();
        if(client.getId() == null) return Mono.empty();
        else{
            client.addsMTP(smtpInfo);
            Client client1 =clientRepository.save(client).block();
        }
        return smtpRepository.save(smtpInfo);
    }

    @Override
    public Mono<SMTPInfo> updateSMTPInfo(String id, SMTPInfo smtpInfoPublisher) {
        smtpInfoPublisher.setId(id);
        return smtpRepository.save(smtpInfoPublisher);
    }

    @Override
    public Mono<SMTPInfo> patchSMTP(String id, SMTPInfo smtpInfoPublisher) {
        SMTPInfo smtpInfo= smtpRepository.findById(id).block();
        if(smtpInfo.getEmail() == smtpInfoPublisher.getEmail() && smtpInfo.getPassword() != smtpInfoPublisher.getPassword()){
            smtpInfo.setEmail(smtpInfo.getEmail());
            smtpInfo.setPassword(smtpInfo.getPassword());
        }

        smtpRepository.save(smtpInfo);

        return Mono.just(smtpInfo);
    }

    @Override
    public Mono<Void> removeSMTP(String id) {
        return smtpRepository.deleteById(id);
    }
}
