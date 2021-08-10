package com.example.messageStore.service;


import com.example.messageStore.entity.Message;
import com.example.messageStore.repository.MessageRepository;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;

import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class SaveMessageService {
  private final MessageRepository messageRepository;

    @KafkaListener(id = "three", topics = "msg3")
    public void msgListener(ConsumerRecord<String, Object> messageReceived) {
        System.out.println(messageReceived.value().toString());

      msgSave(messageReceived.value().toString());
    }
    public void msgSave(String messageReceived){
        Message message=jsonMessageConverter(messageReceived);
     //   messageRepository.deleteAll();
        messageRepository.save(message);
    }
    public Message jsonMessageConverter(String messageReceived){
        Message message=new Gson().fromJson(messageReceived, Message.class);
        return message;
    }
}
