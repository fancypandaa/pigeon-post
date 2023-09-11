package com.pigeon.post.repositories;

import com.pigeon.post.models.IMAPInfo;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IMAPRepository extends ReactiveMongoRepository<IMAPInfo,String> {
    Flux<IMAPInfo> findIMAPInfoByEmail(String email);
}
