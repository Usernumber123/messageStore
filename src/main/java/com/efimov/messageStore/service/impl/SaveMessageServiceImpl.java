package com.efimov.messageStore.service.impl;


import com.efimov.messageStore.entity.Message;
import com.efimov.messageStore.repository.MessageRepository;
import com.efimov.messageStore.service.SaveMessageService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class SaveMessageServiceImpl implements SaveMessageService {
    private final MessageRepository messageRepository;


    public void messageSave(String messageReceived) {
        Message message = jsonMessageConverter(messageReceived);
        messageRepository.save(message);
    }

    public Message jsonMessageConverter(String messageReceived) {
        return new Gson().fromJson(messageReceived, Message.class);
    }
}
