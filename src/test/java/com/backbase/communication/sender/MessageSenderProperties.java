package com.backbase.communication.sender;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "spring.cloud.stream")
@Getter
@Setter
public class MessageSenderProperties {

    private Map<String, Binding> bindings;

    @Getter
    @Setter
    public static class Binding {

        private String destination;

    }
}
