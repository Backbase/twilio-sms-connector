package com.backbase.communication.config;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(initializers = {ConfigDataApplicationContextInitializer.class})
@EnableConfigurationProperties(value = TwilioConfigurationProperties.class)
public class TwilioConfigurationPropertiesTest {
    @Autowired
    private TwilioConfigurationProperties twilioConfigurationProperties;

    @Test
    public void twilioConfigurationPropertiesShouldNotBeNull(){
        Assert.assertEquals("ACAACf90adC9B790Bac79eEFab80E010BD", twilioConfigurationProperties.getAccountSid());
        Assert.assertEquals("token", twilioConfigurationProperties.getAuthToken());
        Assert.assertEquals("123456", twilioConfigurationProperties.getFromNumber());
    }
}
