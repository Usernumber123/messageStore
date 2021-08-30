package com.efimov.messageStore.service;

import com.efimov.messageStore.entity.Message;

public interface SaveMessageService {
    void messageSave(String messageReceived);

    Message jsonMessageConverter(String messageReceived);
}
