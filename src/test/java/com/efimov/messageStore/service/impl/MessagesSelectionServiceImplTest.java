package com.efimov.messageStore.service.impl;

import com.efimov.messageStore.entity.Message;
import com.efimov.messageStore.entity.User;
import com.efimov.messageStore.exceptions.ChatNotFoundException;
import com.efimov.messageStore.model.MessageDto;
import com.efimov.messageStore.repository.MessageRepository;
import com.efimov.messageStore.security.UserDetailsImpl;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MessagesSelectionServiceImplTest {

    public static final String FILTER = "author : Maina, dateAndTime : 2021-08-05 10:42:45,or dateAndTime : 2021-08-05 10:42:13";
    public static final String SMF = "smf";
    public static final String DATE_AND_TIME = "2021-08-05 10:42:45";
    public static final String AUTHOR = "author";
    private final MessageRepository messageRepository = mock(MessageRepository.class);
    SecurityContext securityContext = Mockito.mock(SecurityContext.class);
    Authentication authentication = Mockito.mock(Authentication.class);
    public ConversionService conversionService = mock(ConversionService.class);
    private final RestTemplate restTemplate = mock(RestTemplate.class);

    private final MessagesSelectionServiceImpl messageService = new MessagesSelectionServiceImpl(messageRepository, conversionService, restTemplate);

    @Test
    @SneakyThrows
    void msgRead() {
        String chat = "chatName";
        UserDetailsImpl principal = new UserDetailsImpl(new User());
        List<Message> messages = new ArrayList<>();
        String filter = FILTER;
        Message message = Message.builder().message(SMF).censoredMessage(SMF).dateAndTime(DATE_AND_TIME).author(AUTHOR).build();
        messages.add(message);

        ResponseEntity<String> response = new ResponseEntity<String>(HttpStatus.OK);
        when(messageRepository.findAll((Specification<Message>) any())).thenReturn(messages);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(String.class))).thenReturn(response);
        when(restTemplate.postForObject(anyString(), any(), eq(String.class))).thenReturn("");
        Mockito.when(authentication.getPrincipal()).thenReturn((UserDetailsImpl) principal);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        List<MessageDto> findMsgs = messageService.messageSelectionBy(chat, filter);
        assertSame(findMsgs.size(), 1);

        ResponseEntity<String> response2 = new ResponseEntity<String>(HttpStatus.FORBIDDEN);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(String.class))).thenReturn(response2);
        assertThrows(ChatNotFoundException.class, () -> messageService.messageSelectionBy(chat, filter));
    }
}