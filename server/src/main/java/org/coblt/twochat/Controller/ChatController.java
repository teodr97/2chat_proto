package org.coblt.twochat.Controller;

import commons.Entity.Message;
import org.coblt.twochat.Service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/chat")
    public Message sendMessage(@RequestBody Message message){
        return messageService.saveMessage(message);
        // Render the message to the console
    }

    @GetMapping("/chat")
    public void getMessages() {
        // Get the messages from the DB
        // Render the messages to the console
    }
}
