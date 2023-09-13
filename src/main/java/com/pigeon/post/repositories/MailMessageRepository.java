package com.pigeon.post.repositories;

import com.pigeon.post.models._MailMessage;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MailMessageRepository extends ReactiveMongoRepository<_MailMessage,String> {
}
