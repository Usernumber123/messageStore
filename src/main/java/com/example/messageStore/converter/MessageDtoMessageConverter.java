package com.example.messageStore.converter;

import com.example.messageStore.entity.Message;
import com.example.messageStore.model.MessageDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MessageDtoMessageConverter implements Converter<MessageDto, Message> {

    @Override
    public Message convert(MessageDto messageDto) {
        Message message = new Message();
        message.setMessage(messageDto.getMessage());
        message.setDateAndTime(messageDto.getDateAndTime());
        message.setAuthor(messageDto.getAuthor());
        return message;
    }
}