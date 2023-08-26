package com.pigeon.post.bootstrap;

import com.pigeon.post.models.*;
import com.pigeon.post.repositories.ClientRepository;
import com.pigeon.post.repositories.IMAPRepository;
import com.pigeon.post.repositories.STMPRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootstrapTables implements CommandLineRunner {
    private final ClientRepository clientRepository;
    private final IMAPRepository imapRepository;
    private final STMPRepository stmpRepository;

    public BootstrapTables(ClientRepository clientRepository, IMAPRepository imapRepository, STMPRepository stmpRepository) {
        this.clientRepository = clientRepository;
        this.imapRepository = imapRepository;
        this.stmpRepository = stmpRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("#### LOADING DATA ON BOOTSTRAP #####");
//        clientData();
//        IMAPData();
//        STMPData();
    }
    public void clientData(){
        Client client = Client.builder().build();
        client.setAlias("LOL");
        client.setStatus("Verified");
        client.setBusinessName("LOL");
        client.setPricePackage(PricePackage.FREE);
        client.setBusinessType("Sport");
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
        STMPInfo stmpInfo= STMPInfo.builder().build();
        stmpInfo.setEmail("LOL@mai.com");
        stmpInfo.setPassword("LOL123");
        stmpInfo.setHostProvider(MailProvider.HOTMAIL);
        stmpRepository.save(stmpInfo).block();
    }

}
