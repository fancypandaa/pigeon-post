package com.pigeon.post.repositories;

import com.pigeon.post.models.IMAPInfo;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface IMAPRepository extends ReactiveMongoRepository<IMAPInfo,String> {
    IMAPInfo findIMAPInfoByEmail(String email);
}
