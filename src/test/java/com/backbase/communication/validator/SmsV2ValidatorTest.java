package com.backbase.communication.validator;

import com.backbase.buildingblocks.presentation.errors.BadRequestException;
import com.backbase.buildingblocks.presentation.errors.Error;
import com.backbase.communication.SmsV2Factory;
import com.backbase.communication.util.ErrorCodes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;


@RunWith(MockitoJUnitRunner.class)
public class SmsV2ValidatorTest {

    static final List<Error> ERROR_LIST_O1 = List.of(new Error()
            .withKey(ErrorCodes.ERR_01.getErrorCode())
            .withMessage(ErrorCodes.ERR_01.getErrorMessage()));
    SmsV2Validator smsV2Validator;

    @Test
    public void expect_BadRequestException_whenToIs_Empty() {
        smsV2Validator = new SmsV2Validator();
        try{
            smsV2Validator.validate(SmsV2Factory.emptyToSmsV2());
        }catch(BadRequestException e){
            assertEquals(e.getMessage(), ErrorCodes.ERR_01.getErrorMessage());
            assertEquals(1, e.getErrors().size());
            assertEquals(ERROR_LIST_O1, e.getErrors().stream().collect(Collectors.toList()));
        }
    }

    @Test
    public void expect_BadRequestException_whenFromIs_Empty() {
        smsV2Validator = new SmsV2Validator();
        try{
            smsV2Validator.validate(SmsV2Factory.emptyFromSmsV2());
        }catch(BadRequestException e){
            assertEquals(e.getMessage(), ErrorCodes.ERR_01.getErrorMessage());
            assertEquals(1, e.getErrors().size());
            assertEquals(ERROR_LIST_O1, e.getErrors().stream().collect(Collectors.toList()));
        }
    }
}