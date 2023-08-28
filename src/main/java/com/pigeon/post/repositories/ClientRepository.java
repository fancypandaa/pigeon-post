package com.pigeon.post.repositories;

import com.pigeon.post.models.Client;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ClientRepository extends ReactiveMongoRepository<Client,String> {
}
