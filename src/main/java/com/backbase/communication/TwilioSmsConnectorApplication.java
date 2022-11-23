package com.backbase.communication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableDiscoveryClient
@EnableAsync
public class TwilioSmsConnectorApplication extends SpringBootServletInitializer {

    public static void main(final String[] args) {
        SpringApplication.run(TwilioSmsConnectorApplication.class, args);
    }

}