package com.efimov.messageStore.controller;

import com.efimov.messageStore.api.MsgGetterApi;
import com.efimov.messageStore.model.MessageDto;
import com.efimov.messageStore.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MessagesSelectionController implements MsgGetterApi {
    @Autowired
    MessageService getMessageService;

    @Override
    public ResponseEntity<List<MessageDto>> messageSelectionBy(String filter, String chat) {
        return new ResponseEntity<>(getMessageService.messageSelectionBy(chat,filter), HttpStatus.OK);
    }
}
