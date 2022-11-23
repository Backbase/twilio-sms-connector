package com.backbase.communication.config;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.Range;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "backbase.communication")
@Getter
@Setter
public class MessageChannelProperties {

    private Map<String, Map<Range<Integer>, String>> channels;

}
