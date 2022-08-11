package org.coblt.twochat.Service;

import commons.Entity.Message;
import org.coblt.twochat.Repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService{

    @Autowired
    private MessageRepository repository;

    @Override
    public Message saveMessage(Message message) {
        return repository.save(message);
    }
}
