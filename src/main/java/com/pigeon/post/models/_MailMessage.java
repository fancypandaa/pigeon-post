package com.pigeon.post.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.data.util.Pair;

import javax.mail.Message;
import javax.persistence.Lob;
import java.util.*;

@Document
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize
public class _MailMessage {

    @MongoId
    String id;
    //    mail header
    String messageId;
    String from;
    //    1 or m
    Set<Pair<RecipientType,String>> to= new HashSet<>();
    String date;
    String subject;
    //    CC or m
    Set<String> replyTo= new HashSet<>();
    //    BCC one or many
    Set<String> forwardTo= new HashSet<>();
    //    mail body
    @Lob
    String message;
    String type;
    @Lob
    Byte[] attachment;
    String contentType;
    String attachmentUrl;
//    mail references
    ArrayList<String> references = new ArrayList<>();

    public _MailMessage _to(RecipientType type, String email){
        this.to.add(Pair.of(type,email));
        return this;
    }

    public _MailMessage replyIds(String reply){
        this.replyTo.add(reply);
        return this;
    }
    public _MailMessage forwardIds(String forward){
        this.forwardTo.add(forward);
        return this;
    }
    public _MailMessage references(String messageRef){
        this.references.add(messageRef);
        return this;
    }
}