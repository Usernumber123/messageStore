package com.example.messageStore.service;

import com.example.messageStore.model.MessageDto;

import java.util.List;

public interface MessageService {
    List<MessageDto> msgRead(MessageDto messageDto);
}
