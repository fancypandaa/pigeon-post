package com.pigeon.post.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import java.util.*;
@Document
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailBackBone {
//    HASHTABLE for Mails History
    /*
    in stack FILO
        signs
        root -> ^
        forward -> ~
        reply -> ~~
     */
    @MongoId
    String id;

    String rootMessageId;
    ArrayList<String> messagesIdMirror=new ArrayList<>(); // ex: ^x~y~~z~~m
    ArrayList<String> replyIds=new ArrayList<>();
    ArrayList<String> forwardIds=new ArrayList<>();

    public MailBackBone replyIds(String reply){
        this.replyIds.add(reply);
        return this;
    }
    public MailBackBone forwardIds(String forward){
        this.forwardIds.add(forward);
        return this;
    }
    public MailBackBone messagesIdMirror(String messageIdMirror){
        this.messagesIdMirror.add(messageIdMirror);
        return this;
    }
}
