package com.pigeon.post.repositories;

import com.pigeon.post.models.IMAPInfo;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface IMAPRepository extends ReactiveMongoRepository<IMAPInfo,String> {
    Mono<IMAPInfo> findIMAPInfoByEmail(String email);
}
