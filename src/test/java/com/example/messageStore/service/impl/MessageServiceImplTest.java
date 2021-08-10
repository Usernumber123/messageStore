package com.example.messageStore.service.impl;

import com.example.messageStore.entity.Message;
import com.example.messageStore.model.MessageDto;
import com.example.messageStore.repository.MessageRepository;
import com.example.messageStore.service.impl.specifications.MessageSpecificationsBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
class MessageServiceImplTest {
    /*
    private final MessageRepository messageRepository=mock(MessageRepository.class);

    public ConversionService conversionService=mock(ConversionService.class);

    private final MessageServiceImpl messageService = new MessageServiceImpl(messageRepository,conversionService);
    @Test
    void msgRead() {
        List<Message> messages=new ArrayList<>();
        MessageDto messageDto=new MessageDto();
        messageDto.setMessage("author : Maina, dateAndTime : 2021-08-05 10:42:45,or dateAndTime : 2021-08-05 10:42:13");
        messageDto.setAuthor("Author");
        messageDto.setDateAndTime("2021-08-10 09:42:30");
        Message message=Message.builder().message("smf").censoredMessage("smf").dateAndTime("2021-08-05 10:42:45").author("author").build();
        messages.add(message);
        when(conversionService.convert(messageDto,Message.class)).thenReturn(Message.builder().message("author : Maina, dateAndTime : 2021-08-05 10:42:45,or dateAndTime : 2021-08-05 10:42:13").dateAndTime("2021-08-05 10:42:45").author("Author").build());
        when(messageRepository.findAll((Specification<Message>) any())).thenReturn(messages);
        List<MessageDto> findMsgs=messageService.msgRead(messageDto);
        MessageSpecificationsBuilder builder = new MessageSpecificationsBuilder();
            builder.with();

        assertSame(findMsgs.size(),1);
    }*/
}