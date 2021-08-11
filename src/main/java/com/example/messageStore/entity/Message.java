package com.example.messageStore.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    @Column(name = "message")
    private String message;
    @Column(name = "censoredMessage")
    private String censoredMessage;
    @Column(name = "date_and_time")
    private String dateAndTime;
    @Column(name = "age")
    private Integer age;
}
