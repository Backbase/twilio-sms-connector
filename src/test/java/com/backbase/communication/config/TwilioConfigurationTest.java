package com.backbase.communication.config;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.verify;


@SpringBootTest
@RunWith(SpringRunner.class)
@EnableConfigurationProperties(value = TwilioConfigurationProperties.class)
@ContextConfiguration(classes = TwilioConfiguration.class)
public class TwilioConfigurationTest {

    @Autowired
    private TwilioConfigurationProperties twilioConfigurationProperties;

    @Autowired
    private TwilioConfiguration twilioConfiguration;

    @Test
    public void ensureGivenConfigurationPropertiesTwilioIsConfigured(){
        final TwilioConfiguration twilioConfigurationSpy = Mockito.spy(twilioConfiguration);
        Assertions.assertThat(twilioConfigurationProperties).isNotNull();
        twilioConfigurationSpy.initTwilio();
        verify(twilioConfigurationSpy, Mockito.times(1)).initTwilio();
    }

}
