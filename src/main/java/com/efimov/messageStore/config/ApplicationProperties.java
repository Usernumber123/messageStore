package com.efimov.messageStore.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "main")
@Data
@Primary
public class ApplicationProperties {
    private Hikari hikari;
    private Database database;


    @Data
    public static class Hikari {
        private String jdbcUrl;
        private String driverClassName;
    }

    @Data
    public static class Database {
        private String username;
        private String password;
        private String url;
        private String databaseName;
    }


}
