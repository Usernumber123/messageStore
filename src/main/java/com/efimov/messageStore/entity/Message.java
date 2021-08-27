package com.efimov.messageStore.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "messages")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Message {
    @Id
    @JoinColumn(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "author")
    private String author;
    @Column(name = "chat")
    private String chat;
    @Column(name = "message")
    private String message;
    @Column(name = "censoredMessage")
    private String censoredMessage;
    @Column(name = "date_and_time")
    private String dateAndTime;
    @Column(name = "age")
    private Integer age;
}
