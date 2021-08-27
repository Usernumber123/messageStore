package com.efimov.messageStore.converter;


import com.efimov.messageStore.entity.Message;
import com.efimov.messageStore.model.MessageDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MessageMessageDtoConverter implements Converter<Message, MessageDto> {


    @Override
    public MessageDto convert(Message message) {
        MessageDto messageDto = new MessageDto();
        messageDto.setMessage(message.getCensoredMessage());
        messageDto.setDateAndTime(message.getDateAndTime());
        messageDto.setAge(message.getAge());
        messageDto.setAuthor(message.getAuthor());
        return messageDto;
    }
}
