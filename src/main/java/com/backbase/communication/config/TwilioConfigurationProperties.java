package com.backbase.communication.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotBlank;

@Configuration
@ConfigurationProperties(prefix = "twilio")
@Getter
@Setter
public class TwilioConfigurationProperties {

    @NotBlank(message = "'accountSid' cannot be empty/null")
    private String accountSid;
    @NotBlank(message = "'authToken' cannot be empty/null")
    private String authToken;
    @NotBlank(message = "'fromNumber' cannot be empty/null")
    private String fromNumber;

}
