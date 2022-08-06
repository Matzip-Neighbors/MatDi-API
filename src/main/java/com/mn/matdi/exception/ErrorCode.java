package com.mn.matdi.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ErrorCode {

    // User Verification
    INVALID_INPUT_VALUE(400, "V001", "Invalid Input Value"),
    VERIFICATION_IS_NULL(400, "V002", "Verification Is Null")

    ;
    private final int status;
    private final String code;
    private final String message;
}
