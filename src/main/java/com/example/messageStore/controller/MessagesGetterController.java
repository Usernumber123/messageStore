package com.example.messageStore.controller;

import com.example.messageStore.api.MsgGetterApi;
import com.example.messageStore.model.MessageDto;
import com.example.messageStore.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MessagesGetterController implements MsgGetterApi {
    @Autowired
    MessageService getMessageService;

    @Override
    public ResponseEntity<List<MessageDto>> getMessagesBy(String filter) {
        return new ResponseEntity<>(getMessageService.msgRead(filter), HttpStatus.OK);
    }

}
