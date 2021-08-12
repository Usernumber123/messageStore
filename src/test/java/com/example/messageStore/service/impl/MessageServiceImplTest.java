package com.example.messageStore.service.impl;

import com.example.messageStore.entity.Message;
import com.example.messageStore.model.MessageDto;
import com.example.messageStore.repository.MessageRepository;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
class MessageServiceImplTest {

    private final MessageRepository messageRepository=mock(MessageRepository.class);

    public ConversionService conversionService=mock(ConversionService.class);

    private final MessageServiceImpl messageService = new MessageServiceImpl(messageRepository,conversionService);
    @Test
    void msgRead() {
        List<Message> messages=new ArrayList<>();
        String filter ="author : Maina, dateAndTime : 2021-08-05 10:42:45,or dateAndTime : 2021-08-05 10:42:13";
        Message message=Message.builder().message("smf").censoredMessage("smf").dateAndTime("2021-08-05 10:42:45").author("author").build();
        messages.add(message);
        when(messageRepository.findAll((Specification<Message>) any())).thenReturn(messages);
        List<MessageDto> findMsgs=messageService.msgRead(filter);


        assertSame(findMsgs.size(),1);
    }
}