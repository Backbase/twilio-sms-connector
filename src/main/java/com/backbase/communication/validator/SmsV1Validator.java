package com.backbase.communication.validator;

import com.backbase.buildingblocks.presentation.errors.BadRequestException;
import com.backbase.buildingblocks.presentation.errors.Error;
import com.backbase.communication.model.SmsV1;
import com.backbase.communication.util.ErrorCodes;
import com.backbase.outbound.integration.communications.rest.spec.v1.model.Content;
import com.backbase.outbound.integration.communications.rest.spec.v1.model.Recipient;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Sample validator.
 */
@Component
public class SmsV1Validator {

    /**
     * Simple validation method. <ul> <li>List of recipients should not be empty.</li> <li>List of Content should not be
     * empty.</li> <li>Recipient contentId should link to a Content object</li> </ul>
     */
    public void validate(SmsV1 smsV1) {

        // return BadRequest when list of recipients is empty
        if (CollectionUtils.isEmpty(smsV1.getRecipients())) {
            throw new BadRequestException().withMessage(ErrorCodes.ERR_01.getErrorMessage())
                    .withErrors(List.of(new Error().withKey(ErrorCodes.ERR_01.getErrorCode())
                            .withMessage(ErrorCodes.ERR_01.getErrorMessage())));
        }

        // return BadRequest when list of content is empty
        if (CollectionUtils.isEmpty(smsV1.getContent())) {
            throw new BadRequestException().withMessage(ErrorCodes.ERR_02.getErrorMessage())
                    .withErrors(List.of(new Error().withKey(ErrorCodes.ERR_02.getErrorCode())
                            .withMessage(ErrorCodes.ERR_02.getErrorMessage())));
        }

        // missing content
        for (Recipient recipient : smsV1.getRecipients()) {

            if (smsV1.getContent()
                    .stream()
                    .map(Content::getContentId)
                    .noneMatch(contentId -> Objects.equals(contentId, recipient.getContentId()))) {

                throw new BadRequestException().withMessage(ErrorCodes.ERR_03.getErrorMessage())
                        .withErrors(List.of(new Error().withKey(ErrorCodes.ERR_03.getErrorCode())
                                .withMessage(ErrorCodes.ERR_03.getErrorMessage())
                                .withContext(Map.of("contentId", recipient.getContentId()))));
            }
        }

    }

}
