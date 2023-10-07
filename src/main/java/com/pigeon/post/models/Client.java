package com.pigeon.post.models;

import com.mongodb.lang.NonNull;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
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
    @DBRef
    private List<IMAPInfo> iMAPs=new ArrayList<>();
    @DBRef
    private List<SMTPInfo> sMTPs=new ArrayList<>();

    public Client addiMAPs(IMAPInfo iMAP){
        this.iMAPs.add(iMAP);
        return this;
    }
    public Client addsMTP(SMTPInfo sMTP){
        this.sMTPs.add(sMTP);
        return this;
    }
}
