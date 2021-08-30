package com.efimov.messageStore;

import com.efimov.messageStore.config.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({ApplicationProperties.class})
public class MessageStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(MessageStoreApplication.class, args);
    }

}
