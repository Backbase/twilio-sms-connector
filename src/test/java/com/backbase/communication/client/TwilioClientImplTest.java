package com.backbase.communication.client;

import com.backbase.communication.model.SmsRequest;
import com.twilio.type.PhoneNumber;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@ActiveProfiles("it")
public class TwilioClientImplTest {

    static {
        System.setProperty("SIG_SECRET_KEY", "JWTSecretKeyDontUseInProduction!");
    }
    
    @MockBean
    private TwilioClientImpl twilioClient;
    
    private SmsRequest request;

	@Before
	public void setUp() {
		request = SmsRequest.builder()
				.fromNumber(new PhoneNumber("454339389"))
				.toNumber(new PhoneNumber("124565568"))
				.message("Test message")
				.build();
	}

	@Test
	public void testSendSms() {
		twilioClient.sendSms(request);
		verify(twilioClient, times(1)).sendSms(any());
	}

}
