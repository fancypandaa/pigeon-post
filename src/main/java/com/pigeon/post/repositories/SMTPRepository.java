package com.pigeon.post.repositories;

import com.pigeon.post.models.IMAPInfo;
import com.pigeon.post.models.SMTPInfo;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SMTPRepository extends ReactiveMongoRepository<SMTPInfo,String> {
    Flux<SMTPInfo> findSMTPInfoByEmail(String email);

}
