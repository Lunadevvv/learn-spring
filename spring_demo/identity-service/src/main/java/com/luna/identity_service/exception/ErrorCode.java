package com.luna.identity_service.exception;

public enum ErrorCode {
    USERNAME_INVALID(1003, "Username must be at least 3 characters"),
    UNCATEGORIZE_EXCEPTION(9999, "Uncategorize exception"),
    USER_EXISTED(1001, "User existed"),
    PASSWORD_INVALID(1003, "Password must be at least 8 characters"),
    INVALID_KEY(1004,"Invalid exception-enum key"),
    USER_NOT_EXISTED(1005, "User not existed"),
    UNAUTHENTICATED(1006, "Unauthenticated"),

    ;

    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
