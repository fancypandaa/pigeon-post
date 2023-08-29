package com.pigeon.post.models;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.*;

@Document
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    @MongoId
    private String id;
    private String businessName;
    private String businessType;
    @Enumerated(value = EnumType.STRING)
    private PricePackage pricePackage;
    private String status;
    private String alias;
    @DocumentReference
    private Set<IMAPInfo> iMAPs=new HashSet<>();
    @DocumentReference
    private Set<SMTPInfo> sMTPs=new HashSet<>();

    public Client addiMAPs(IMAPInfo iMAP){
        this.iMAPs.add(iMAP);
        return this;
    }
    public Client addsMTP(SMTPInfo sMTP){
        this.sMTPs.add(sMTP);
        return this;
    }
}
