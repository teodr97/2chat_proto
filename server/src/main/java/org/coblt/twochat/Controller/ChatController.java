package org.coblt.twochat.Controller;

import commons.Entity.Message;
import org.coblt.twochat.Service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController
public class ChatController {

    @Autowired
    private MessageService messageService;

    // The messages that have been received from the other party but have not been drawn on screen yet.
    private LinkedList<Message> pendingMessages = new LinkedList<>();

    @PostMapping("/chat")
    public ResponseEntity<Message> sendMessage(@RequestBody Message message){
        pendingMessages.add(message);
        return ResponseEntity.ok(messageService.saveMessage(message));
        // Render the message to the console
    }

    @GetMapping("/chat")
    public ResponseEntity<List<Message>> getPendingMessages() {
        List<Message> returnedList = new LinkedList<>(pendingMessages);

        pendingMessages.clear();
        return ResponseEntity.ok(returnedList);
        // Get the messages from the DB that have not been drawn yet.
        // Render the messages to the console
    }
}
