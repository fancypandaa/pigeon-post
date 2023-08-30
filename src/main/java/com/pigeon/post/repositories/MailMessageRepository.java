package com.pigeon.post.repositories;

import com.pigeon.post.models.MailMessage;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MailMessageRepository extends ReactiveMongoRepository<MailMessage,String> {
}
