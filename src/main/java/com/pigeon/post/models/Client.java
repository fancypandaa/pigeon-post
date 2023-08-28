package com.pigeon.post.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;

@Data
@Document
@Builder
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
}
