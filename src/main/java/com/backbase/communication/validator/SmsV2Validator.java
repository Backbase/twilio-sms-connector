package com.backbase.communication.validator;

import com.backbase.buildingblocks.presentation.errors.BadRequestException;
import com.backbase.buildingblocks.presentation.errors.Error;
import com.backbase.communication.model.SmsV2;
import com.backbase.communication.util.ErrorCodes;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Sample validator.
 */
@Component
public class SmsV2Validator {

    /**
     * Simple validation method. <ul> <li>List of recipients should not be empty.</li></ul>
     */
    public void validate(SmsV2 smsV2) {
        // return BadRequest when list of recipients is empty
        if (isEmptyRecipient(smsV2)) {
            throw new BadRequestException().withMessage(ErrorCodes.ERR_01.getErrorMessage())
                    .withErrors(List.of(new Error().withKey(ErrorCodes.ERR_01.getErrorCode())
                            .withMessage(ErrorCodes.ERR_01.getErrorMessage())));
        }

    }

    private boolean isEmptyRecipient(SmsV2 smsV2) {
        if(smsV2.getTo().isEmpty())
            return true;
        return StringUtils.isEmpty(smsV2.getFrom());
    }

}
