package com.efimov.messageStore.controller;

import com.efimov.messageStore.api.MsgGetterApi;
import com.efimov.messageStore.model.MessageDto;
import com.efimov.messageStore.service.MessagesSelectionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class MessagesSelectionController implements MsgGetterApi {

    private final Map<String, MessagesSelectionService> messagesSelectionServices;

    public MessagesSelectionController(Map<String, MessagesSelectionService> messagesSelectionServices) {
        this.messagesSelectionServices = messagesSelectionServices;
    }

    @Override
    public ResponseEntity<List<MessageDto>> messageSelectionBy(String rights, String filter, String chat) {
        return new ResponseEntity<>(messagesSelectionServices.get(rights).messageSelectionBy(chat, filter), HttpStatus.OK);
    }
}
