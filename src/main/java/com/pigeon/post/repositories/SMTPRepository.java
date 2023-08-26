package com.pigeon.post.repositories;

import com.pigeon.post.models.SMTPInfo;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface SMTPRepository extends ReactiveMongoRepository<SMTPInfo,String> {
}
