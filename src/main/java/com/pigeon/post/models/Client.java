package com.pigeon.post.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@Document
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    @Id
    private String Id;
    private String businessName;
    private String businessType;
    @Enumerated(value = EnumType.STRING)
    private PricePackage pricePackage;
    private String Status;
    private String alias;
}
