package com.efimov.messageStore.service.impl;

import com.efimov.messageStore.service.SaveMessageService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsumerMessageServiceImpl {
    public static final String LISTENER_ID_5 = "listenerId5";
    public static final String MESSAGE_FOR_STORE_TOPIC = "MessageForStoreTopic";
    private final SaveMessageService saveMessageService;

    @KafkaListener(id = LISTENER_ID_5, topics = MESSAGE_FOR_STORE_TOPIC)
    public void msgListener(ConsumerRecord<String, Object> messageReceived) {
        saveMessageService.messageSave(messageReceived.value().toString());
    }
}
