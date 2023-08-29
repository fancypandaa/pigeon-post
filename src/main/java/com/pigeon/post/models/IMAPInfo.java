package com.pigeon.post.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@Document
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IMAPInfo {
    @MongoId
    private String id;
    @Indexed(unique = true)
    private String email;
    private String password;
    @Enumerated(value = EnumType.STRING)
    private MailProvider hostProvider;
    private String Storage;
    private Long usage;


}
