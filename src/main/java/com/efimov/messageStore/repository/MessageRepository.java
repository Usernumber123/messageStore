package com.efimov.messageStore.repository;

import com.efimov.messageStore.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long>, JpaSpecificationExecutor<Message> {
    List<Message> findAll();

    @Query("from Message where author = ?1 ")
    List<Message> findAllByUserLogin(String login);

    @Query(value = "select * from messages where TO_TIMESTAMP(date_and_time, 'YYYY-MM-DD HH24:MI:SS') <= TO_TIMESTAMP(?1,'YYYY-MM-DD HH24:MI:SS')", nativeQuery = true)
    List<Message> getAllMessagesBeforeDate(String date1);

    @Query(value = "select * from messages where TO_TIMESTAMP(date_and_time, 'YYYY-MM-DD HH24:MI:SS') >= TO_TIMESTAMP(:date ,'YYYY-MM-DD HH24:MI:SS')", nativeQuery = true)
    List<Message> getAllMessagesAfterDate(@Param("date") String date1);
}
