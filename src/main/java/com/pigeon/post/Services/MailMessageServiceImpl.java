package com.pigeon.post.Services;

import com.pigeon.post.models._MailMessage;
import com.pigeon.post.repositories.MailMessageRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MailMessageServiceImpl implements MailMessageService{
    private final MailMessageRepository mailMessageRepository;

    public MailMessageServiceImpl(MailMessageRepository mailMessageRepository) {
        this.mailMessageRepository = mailMessageRepository;
    }


    @Override
    public Flux<_MailMessage> listAllMailMsg() {
        return mailMessageRepository.findAll();
    }

    @Override
    public Mono<_MailMessage> getMailById(String id) {
        return mailMessageRepository.findById(id);
    }

    @Override
    public Mono<_MailMessage> createNewMail(_MailMessage mailMessage) {
        return mailMessageRepository.save(mailMessage);
    }

    @Override
    public Mono<_MailMessage> updateMailMsg(String id, _MailMessage mailMessage) {
        mailMessage.setMessageId(id);
        return mailMessageRepository.save(mailMessage);
    }

    @Override
    public Mono<_MailMessage> patchMailMsg(String id, _MailMessage mailMessage) {
        _MailMessage message= mailMessageRepository.findById(id).block();
        if(mailMessage.getFrom() != null) {
            message.setFrom(mailMessage.getFrom());
        }
        if(mailMessage.getTO() != null) {
            message.setTO(mailMessage.getTO());
        }
        if(mailMessage.getCC() != null) {
            message.setCC(mailMessage.getCC());
        }
        if(mailMessage.getBCC() != null) {
            message.setBCC(mailMessage.getBCC());
        }
        if(mailMessage.getDate() != null) {
            message.setDate(mailMessage.getDate());
        }
        if(mailMessage.getSubject() != null) {
            message.setSubject(mailMessage.getSubject());
        }

        if(mailMessage.getReplyTo() != null) {
            message.setReplyTo(mailMessage.getReplyTo());
        }
        if(mailMessage.getForwardTo() != null) {
            message.setForwardTo(mailMessage.getForwardTo());
        }
        if(mailMessage.getMessage() != null){
            message.setMessage(mailMessage.getMessage());
        }
        return mailMessageRepository.save(message);

    }

    @Override
    public Mono<Void> removeMailMsg(String id) {
        return mailMessageRepository.deleteById(id);
    }
}
