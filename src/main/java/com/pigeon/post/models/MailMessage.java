package com.pigeon.post.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.persistence.Lob;
import java.util.*;

@Document
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailMessage {

    @MongoId
    String id;
//    mail header
    String messageId;
    String from;
    String to;
    String date;
    String subject;
    String replyTo;
    String forwardTo;
    String contentType;
//    mail body
    @Lob
    String message;
    @Lob
    Byte[] attachment;
//    mail references
    ArrayList<Long> references = new ArrayList<>();


}