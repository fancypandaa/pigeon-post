package com.pigeon.post.repositories;

import com.pigeon.post.models.MailBackBone;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MailBackBoneRepository extends ReactiveMongoRepository<MailBackBone,String> {
    MailBackBone findMailBackBoneByRootMessageId(String rootMessageId);
}
