package com.example.messageStore.repository;

import com.example.messageStore.MessageStoreApplication;
import com.example.messageStore.entity.Message;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ContextConfiguration(classes= MessageStoreApplication.class)
class MessageRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private MessageRepository messageRepository;

    @Test
    void findAllSpec() {
        Message message1=Message.builder().message("Author1").censoredMessage("smf").age(12).dateAndTime("2021-08-05 10:42:45").author("author").build();
        Message message2=Message.builder().message("Author2").censoredMessage("smf").age(12).dateAndTime("2021-08-05 10:42:45").author("author").build();
        entityManager.persist(message1);
        entityManager.persist(message2);
        entityManager.flush();
        List<Message> found = messageRepository.findAll();


    }
}