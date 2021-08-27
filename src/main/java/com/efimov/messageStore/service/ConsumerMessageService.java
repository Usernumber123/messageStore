package com.efimov.messageStore.service;

import com.efimov.messageStore.service.impl.ConsumerMessageServiceImpl;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface ConsumerMessageService {
    void msgListener(ConsumerRecord<String, Object> messageReceived);
}
