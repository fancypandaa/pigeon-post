package com.pigeon.post.repositories;

import com.pigeon.post.models.STMPInfo;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface STMPRepository extends ReactiveMongoRepository<STMPInfo,String> {
}
