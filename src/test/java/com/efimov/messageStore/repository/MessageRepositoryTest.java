package com.efimov.messageStore.repository;

import com.efimov.messageStore.MessageStoreApplication;
import com.efimov.messageStore.entity.Message;
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
    public static final String MSG_AUTHOR_1 = "MsgAuthor1";
    public static final String MSG_AUTHOR_2 = "MsgAuthor2";
    public static final String SMF = "smf";
    public static final String DATE_AND_TIME = "2021-08-05 10:42:47";
    public static final String DATE_AND_TIME1 = "2021-08-05 10:42:45";
    public static final String AUTHOR_1 = "Author1";
    public static final String AUTHOR_2 = "Author2";
    public static final String DATE_1 = "2021-08-05 10:42:46";
    public static final int AGE = 12;
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private MessageRepository messageRepository;

    @Test
    void findAllSpec() {
        Message message1=Message.builder().message(MSG_AUTHOR_1).censoredMessage(SMF).age(AGE).dateAndTime(DATE_AND_TIME).author(AUTHOR_1).build();
        Message message2=Message.builder().message(MSG_AUTHOR_2).censoredMessage(SMF).age(AGE).dateAndTime(DATE_AND_TIME1).author(AUTHOR_2).build();
        entityManager.persist(message1);
        entityManager.persist(message2);
        entityManager.flush();
        List<Message> found = messageRepository.findAll();
        assertSame(found.size(),2);
        List<Message> found1 = messageRepository.findAllByUserLogin(AUTHOR_1);
        assertSame(found1.size(),1);
        List<Message> found2 = messageRepository.getAllMessagesAfterDate(DATE_1);
        assertSame(found2.size(),1);
        List<Message> found3 = messageRepository.getAllMessagesBeforeDate(DATE_1);
        assertSame(found2.size(),1);


    }
}