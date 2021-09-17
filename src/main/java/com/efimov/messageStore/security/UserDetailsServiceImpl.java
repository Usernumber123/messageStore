package com.efimov.messageStore.security;

import com.efimov.messageStore.entity.User;
import com.efimov.messageStore.repository.MessageRepository;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private CacheManager cacheManager;

    @SneakyThrows
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
            return new UserDetailsImpl((User) cacheManager.getCache("Users").get(login).get());
        }


}

  /*  @KafkaListener(id = "5", topics = "auth")
    public void addUsers(ConsumerRecord<String, Object> messageReceived) {
        User user = new Gson().fromJson(messageReceived.value().toString(), User.class);
        userRepository.save(user);
    }*/