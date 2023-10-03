package com.pigeon.post.models;

import com.mongodb.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
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
    @Nullable
    private String email;
    private String password;
    @Enumerated(value = EnumType.STRING)
    private IMAPProvider hostProvider;
    private String storage;
    private Long usage;
    @Enumerated(value = EnumType.STRING)
    private Status status;

}
