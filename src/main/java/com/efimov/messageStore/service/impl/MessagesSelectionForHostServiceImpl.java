package com.efimov.messageStore.service.impl;

import com.efimov.messageStore.entity.Message;
import com.efimov.messageStore.model.MessageDto;
import com.efimov.messageStore.repository.MessageRepository;
import com.efimov.messageStore.security.UserDetailsImpl;
import com.efimov.messageStore.service.MessagesSelectionService;
import lombok.SneakyThrows;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Service("Host")
public class MessagesSelectionForHostServiceImpl extends MessagesSelectionServiceImpl implements MessagesSelectionService {

    private static final String HOST = "host";

    public MessagesSelectionForHostServiceImpl(ConversionService conversionService,MessageRepository messageRepository,  RestTemplate restTemplate) {
        super(conversionService,messageRepository,  restTemplate);
    }


    @Override
    protected List<MessageDto> convertMessagesToMessageDtos(List<Message> messages) {
        List<MessageDto> messageDtos = new ArrayList<>();
        for (Message message1 : messages) {
            MessageDto messageDto = conversionService.convert(message1, MessageDto.class);
            messageDto.setMessage(message1.getMessage());
            messageDtos.add(messageDto);
        }
        return messageDtos;
    }

    @SneakyThrows
    @Override
    protected boolean verificationByRest(String chat) {
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(ORCHESTRATOR_URL)
                .queryParam(CHAT_NAME, chat)
                .queryParam(USER_LOGIN, principal.getUser().getLogin())
                .queryParam(HOST, HOST);
        return super.sendRequestToOrchestrator(uriBuilder.toUriString());
    }
}


