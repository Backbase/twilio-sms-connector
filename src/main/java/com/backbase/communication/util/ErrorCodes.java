package com.backbase.communication.util;


import lombok.AllArgsConstructor;

/**
 * List of validation error codes.
 */
@AllArgsConstructor
public enum ErrorCodes {

    ERR_01("comms.error.NO_RECIPIENTS", "List of recipients must not be empty."),
    ERR_02("comms.error.NO_CONTENT", "List of content must not be empty."),
    ERR_03("comms.error.MISSING_CONTENT_REF",
            "Recipient contentId does not reference a valid/existing Content object.");

    private final String errorCode;
    private final String errorMessage;

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
