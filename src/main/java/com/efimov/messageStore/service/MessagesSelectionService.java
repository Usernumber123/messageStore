package com.efimov.messageStore.service;

import com.efimov.messageStore.model.MessageDto;

import java.util.List;

public interface MessagesSelectionService {
    List<MessageDto> messageSelectionBy(String chat, String filter);
}
