package com.pigeon.post.models;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
import com.fasterxml.jackson.databind.annotation.JsonTypeResolver;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.models.parameters.SerializableParameter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.data.util.Pair;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.lang.model.element.Element;
import javax.persistence.Lob;
import java.io.Serializable;
import java.util.*;

@Document
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class _MailMessage {

    @MongoId
    String id;
    //    mail header
    String messageId;
    String from;
    //    1 or m
    Set<String> TO= new HashSet<>();
    Set<String> CC= new HashSet<>();
    Set<String> BCC= new HashSet<>();

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
    String contentType;

    @Lob
    MultipartFile attachment;
    String attachmentId;
    String attachmentUrl;
//    mail references
    ArrayList<String> references = new ArrayList<>();

    public _MailMessage _TO_( String email){
        this.TO.add(email);
        return this;
    }
    public _MailMessage _CC_( String email){
        this.CC.add(email);
        return this;
    }
    public _MailMessage _BCC_( String email){
        this.BCC.add(email);
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