package com.mosaiker.recordservice.repository;

import com.mosaiker.recordservice.entity.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends MongoRepository<Message,String> {
    List<Message> findAllByReceiverUId(Long uId);

    List<Message> findAllByReceiverUIdAndHasReadFalse(Long uId);

    List<Message> findAllByReceiverUIdAndType(Long uId, int type);

    List<Message> findAllByReceiverUIdAndTypeAndHasReadFalse(Long uId, int type);
}
