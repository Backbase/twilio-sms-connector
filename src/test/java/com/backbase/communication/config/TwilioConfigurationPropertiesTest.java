package com.backbase.communication.config;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application.properties")
@EnableConfigurationProperties(value = TwilioConfigurationProperties.class)
public class TwilioConfigurationPropertiesTest {

    @Autowired
    private TwilioConfigurationProperties twilioConfigurationProperties;

    @Test
    public void twilioConfigurationPropertiesShouldNotBeNull(){
        Assert.assertEquals("dummySid", twilioConfigurationProperties.getAccountSid());
        Assert.assertEquals("dummyToken", twilioConfigurationProperties.getAuthToken());
        Assert.assertEquals("dummyNumber", twilioConfigurationProperties.getFromNumber());
    }
}
