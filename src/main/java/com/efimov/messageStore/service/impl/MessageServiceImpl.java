package com.efimov.messageStore.service.impl;

import com.efimov.messageStore.exceptions.ChatNotFoundException;
import com.efimov.messageStore.security.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import com.efimov.messageStore.entity.Message;
import com.efimov.messageStore.model.MessageDto;
import com.efimov.messageStore.repository.MessageRepository;
import com.efimov.messageStore.service.MessageService;
import com.efimov.messageStore.service.impl.specifications.MessageSpecificationsBuilder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.json.JSONObject;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageServiceImpl implements MessageService {

    public static final String AUTHORIZATION = "Authorization";
    public static final String CHAT_NAME = "chatName";
    public static final String USER_LOGIN = "userLogin";
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";
    public static final String ADMIN = "admin";
    private final MessageRepository messageRepository;
    private final ConversionService conversionService;
    private final   RestTemplate restTemplate;

    @SneakyThrows
    @Override
    public List<MessageDto> messageSelectionBy(String chat, String filter) {

        if (verificationByRest(chat)) {


            List<Message> messages = msgFilter(filter);

            List<MessageDto> messageDtos = new ArrayList<>();
            for (Message message1 : messages) {
                messageDtos.add(conversionService.convert(message1, MessageDto.class));
            }
            return messageDtos;
        } else {
          throw new ChatNotFoundException("User does not have this chat");
        }
    }

    private List<Message> msgFilter(String filter) {
        MessageSpecificationsBuilder builder = new MessageSpecificationsBuilder();
        Pattern pattern = Pattern.compile("(or )?(\\w+?) (:|<|>) (\\w+?(-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2})?),");
        Matcher matcher = pattern.matcher(filter + ",");
        while (matcher.find()) {
            builder.with(matcher.group(2), matcher.group(3), matcher.group(4), matcher.group(1) != null);
        }
        Specification<Message> spec = builder.build();

        return messageRepository.findAll(spec);

    }
    @SneakyThrows
    private boolean verificationByRest(String chat) {
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        String OrchestratorUrl
                = "http://localhost:8081/checkChat";
        String OrchestratorUrlGetToken
                = "http://localhost:8081/token";

        JSONObject personJsonObject = new JSONObject();
        personJsonObject.put(LOGIN, ADMIN);
        personJsonObject.put(PASSWORD, ADMIN);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(OrchestratorUrl)
                .queryParam(CHAT_NAME, chat)
                .queryParam(USER_LOGIN, principal.getUser().getLogin());
        HttpEntity entity = new HttpEntity(headers);

        HttpEntity<String> request =
                new HttpEntity<>(personJsonObject.toString(), headers);

        String adminToken =
                restTemplate.postForObject(OrchestratorUrlGetToken, request, String.class);
        headers.set(AUTHORIZATION, "Bearer " + adminToken );
        try {
            ResponseEntity<String> response = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET, entity, String.class);
            return response.getStatusCode().equals(HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            return false;
        }
    }


}
