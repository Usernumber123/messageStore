package com.efimov.messageStore.converter;

import com.efimov.messageStore.entity.Message;
import com.efimov.messageStore.model.MessageDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MessageDtoMessageConverter implements Converter<MessageDto, Message> {

    @Override
    public Message convert(MessageDto messageDto) {
        Message message = new Message();
        message.setMessage(messageDto.getMessage());
        message.setAge(messageDto.getAge());
        message.setDateAndTime(messageDto.getDateAndTime());
        message.setAuthor(messageDto.getAuthor());
        return message;
    }
}