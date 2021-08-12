package com.example.messageStore.service.impl;

import com.example.messageStore.entity.Message;
import com.example.messageStore.model.MessageDto;
import com.example.messageStore.repository.MessageRepository;
import com.example.messageStore.service.MessageService;
import com.example.messageStore.service.impl.specifications.MessageSpecificationsBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final ConversionService conversionService;


    public List<MessageDto> msgRead(String filter) {
        List<Message> messages =msgFilter(filter);
        
        List<MessageDto> messageDtos = new ArrayList<>();
        for (Message message1 : messages) {
            messageDtos.add(conversionService.convert(message1, MessageDto.class));
        }
        return messageDtos;
    }
    private List<Message> msgFilter(String filter){
//        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        MessageSpecificationsBuilder builder = new MessageSpecificationsBuilder();
        Pattern pattern = Pattern.compile("(or )?(\\w+?) (:|<|>) (\\w+?(-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2})?),");
        Matcher matcher = pattern.matcher( filter+",");
        while (matcher.find()) {
            builder.with(matcher.group(2), matcher.group(3), matcher.group(4),matcher.group(1)!=null);
        }
        Specification<Message> spec = builder.build();

       return messageRepository.findAll(spec);

    }


}
