package com.pigeon.post.bootstrap;

import com.pigeon.post.models.*;
import com.pigeon.post.repositories.ClientRepository;
import com.pigeon.post.repositories.IMAPRepository;
import com.pigeon.post.repositories.SMTPRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.Optional;
@Component
public class BootstrapTables implements CommandLineRunner {
    private final ClientRepository clientRepository;
    private final IMAPRepository imapRepository;
    private final SMTPRepository SMTPRepository;

    public BootstrapTables(ClientRepository clientRepository, IMAPRepository imapRepository, SMTPRepository SMTPRepository) {
        this.clientRepository = clientRepository;
        this.imapRepository = imapRepository;
        this.SMTPRepository = SMTPRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("#### LOADING DATA ON BOOTSTRAP #####");
//        clientData();
//        IMAPData();
//        STMPData();
    }
    public void clientData(){
        Client client = new Client();
        client.setAlias("LOL");
        client.setStatus("Verified");
        client.setBusinessName("LOL");
        client.setPricePackage(PricePackage.FREE);
        client.setBusinessType("Sport");
        Optional<IMAPInfo> imapInfoOptional= imapRepository.findById("64ed176dfb1470579e229216").blockOptional();
        if(!imapInfoOptional.isPresent()){
            throw new RuntimeException("Expected imapInfoOptional Not Found");
        }
        Optional<SMTPInfo> smtpInfoOptional= SMTPRepository.findById("64ecb800ae8a5b15d3c2ee23").blockOptional();
        if(!smtpInfoOptional.isPresent()){
            throw new RuntimeException("Expected imapInfoOptional Not Found");
        }
//
        client.getSMTPs().add(smtpInfoOptional.get());
        client.getIMAPs().add(imapInfoOptional.get());

        clientRepository.save(client).block();
    }

    public void IMAPData(){
        IMAPInfo imapInfo= IMAPInfo.builder().build();
        imapInfo.setEmail("LOL@mai.com");
        imapInfo.setPassword("LOL123");
        imapInfo.setStorage("10GB");
        imapInfo.setHostProvider(MailProvider.HOTMAIL);
        imapInfo.setUsage(2L);
        imapRepository.save(imapInfo).block();
    }
    public void STMPData(){
        SMTPInfo smtpInfo = SMTPInfo.builder().build();
        smtpInfo.setEmail("LOL@mai.com");
        smtpInfo.setPassword("LOL123");
        smtpInfo.setHostProvider(MailProvider.HOTMAIL);
        SMTPRepository.save(smtpInfo).block();
    }

}
