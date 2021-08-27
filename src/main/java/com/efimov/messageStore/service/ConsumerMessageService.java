package com.efimov.messageStore.service;

import com.efimov.messageStore.service.impl.ConsumerMessageServiceImpl;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;

public interface ConsumerMessageService {
    @KafkaListener(id = ConsumerMessageServiceImpl.LISTENER_ID_5, topics = ConsumerMessageServiceImpl.MESSAGE_FOR_STORE_TOPIC)
    void msgListener(ConsumerRecord<String, Object> messageReceived);
}
