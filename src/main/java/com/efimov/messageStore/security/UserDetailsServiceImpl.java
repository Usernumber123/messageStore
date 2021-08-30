package com.efimov.messageStore.security;

import com.efimov.messageStore.entity.User;
import com.efimov.messageStore.repository.UserRepository;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return new UserDetailsImpl(userRepository.findOneByLogin(login)
                .orElseThrow(IllegalArgumentException::new));
    }

    @KafkaListener(id = "5", topics = "auth")
    public void addUsers(ConsumerRecord<String, Object> messageReceived) {
        User user = new Gson().fromJson(messageReceived.value().toString(), User.class);
        userRepository.save(user);
    }
}