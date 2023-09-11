package com.pigeon.post.Services;

import com.pigeon.post.models.MailBackBone;
import com.pigeon.post.models.MailMessage;
import com.pigeon.post.repositories.MailBackBoneRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MailBackBoneImpl implements MailBackBoneService {
    private final MailBackBoneRepository mailBackBoneRepository;

    public MailBackBoneImpl(MailBackBoneRepository mailBackBoneRepository) {
        this.mailBackBoneRepository = mailBackBoneRepository;
    }

    @Override
    public Flux<MailBackBone> listAllMailLogs() {
        return mailBackBoneRepository.findAll();
    }

    @Override
    public Mono<MailBackBone> getMailsTree(String id) {
        return mailBackBoneRepository.findById(id);
    }

    @Override
    public Mono<MailBackBone> getMailByRootId(String id) {
        return Mono.just(mailBackBoneRepository.findMailBackBoneByRootMessageId(id));
    }

    @Override
    public Mono<MailBackBone> createNewMailBB(MailBackBone mailMessage) {
        return mailBackBoneRepository.save(mailMessage);
    }

    @Override
    public Mono<MailBackBone> updateMailBB(String id, MailBackBone mailMessage) {
        mailMessage.setId(id);
        return mailBackBoneRepository.save(mailMessage);
    }

    @Override
    public Mono<Void> removeMailBB(String id) {
        return mailBackBoneRepository.deleteById(id);
    }
}
