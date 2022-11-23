package com.backbase.communication.mapper;

import com.backbase.communication.model.SmsRequest;
import com.backbase.outbound.integration.communications.rest.spec.v1.model.Content;
import com.backbase.outbound.integration.communications.rest.spec.v1.model.Recipient;
import com.twilio.type.PhoneNumber;
import org.apache.commons.lang.text.StrSubstitutor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class SmsV1Mapper {

    private static final String SPLIT_REGEX = ":";
    private static final String PLACEHOLDER_PREFIX = "${";
    private static final String PLACEHOLDER_SUFFIX = "}";
    
    public List<SmsRequest> toSmsRequest(Recipient recipient, Content content){
    	 return recipient.getTo().stream().map(toNumber -> SmsRequest.builder()
    		.fromNumber(new PhoneNumber (recipient.getFrom()))
    		.toNumber(new PhoneNumber(toNumber.split(SPLIT_REGEX).length > 1 ? toNumber.split(SPLIT_REGEX)[1] : toNumber))
    		.message(new StrSubstitutor(recipient.getData(), PLACEHOLDER_PREFIX, PLACEHOLDER_SUFFIX).replace(content.getBody()))
    		.build()    		   		
    	).collect(Collectors.toList());
    }

}
