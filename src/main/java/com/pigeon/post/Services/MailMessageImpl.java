package com.pigeon.post.Services;

import com.pigeon.post.models.MailBackBone;
import com.pigeon.post.models._MailMessage;
import com.pigeon.post.repositories.MailMessageRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@Service
public class MailMessageImpl implements MailMessageService{
    private final MailMessageRepository mailMessageRepository;
    private final MailBackBoneService mailBackBoneService;

    public MailMessageImpl(MailMessageRepository mailMessageRepository, MailBackBoneService mailBackBoneService) {
        this.mailMessageRepository = mailMessageRepository;
        this.mailBackBoneService = mailBackBoneService;
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
//        MailBackBone mailLog= mailBackBoneService.getMailByRootId(mailMessage.getMessageId()).block();
//        if(mailLog.getId() != null){
//            mailLog.getMessagesIdMirror().add(mailMessage.getMessageId());
//            if(mailMessage.getReplyTo() != null ) mailLog.setReplyIds((ArrayList<String>) mailMessage.getReplyTo());
//            if(mailMessage.getForwardTo() != null) mailLog.setForwardIds((ArrayList<String>) mailMessage.getForwardTo());
//            if(mailMessage.getReferences() != null) {
//              mailMessage.getReferences().forEach(mMessage -> mailLog.getMessagesIdMirror().add(mMessage.toString()));
//            }
//        }
//        else {
//            MailBackBone mailBackBone= new MailBackBone();
//            mailBackBone.setRootMessageId(mailMessage.getMessageId());
//            mailBackBone.getMessagesIdMirror().add(mailMessage.getMessageId());
//            if(mailMessage.getReplyTo() != null) mailBackBone.setReplyIds((ArrayList<String>) mailMessage.getReplyTo());
//            if(mailMessage.getForwardTo() != null) mailBackBone.setForwardIds((ArrayList<String>) mailMessage.getForwardTo());
//            if(mailMessage.getReferences() != null) {
//                mailMessage.getReferences().forEach(mMessage -> mailBackBone.getMessagesIdMirror().add(mMessage.toString()));
//            }
//            mailBackBoneService.createNewMailBB(mailBackBone);
//        }
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
