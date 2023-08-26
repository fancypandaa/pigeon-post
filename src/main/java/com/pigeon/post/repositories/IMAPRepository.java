package com.pigeon.post.repositories;

import com.pigeon.post.models.IMAPInfo;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface IMAPRepository extends ReactiveMongoRepository<IMAPInfo,String> {
}
