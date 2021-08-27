package com.efimov.messageStore.service;

import com.efimov.messageStore.model.MessageDto;

import java.util.List;

public interface MessageService {
    List<MessageDto>  messageSelectionBy(String chat,String filter);
}
