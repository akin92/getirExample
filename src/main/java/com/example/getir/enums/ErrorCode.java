package com.example.getir.enums;

public enum ErrorCode {
    DATABASE(0, "A database error has occurred."),
    USER_NOT_EXISTS(1, "User does not exist. User should register.."),
    DUPLICATE_USER(2, "This user already exists."),
    INVALID_TOKEN(3, "Auth Token is invalid."),
    EMPTY_TOKEN(4, "Auth Token is empty."),
    INVALID_STOCK(5, "There is no stock for book.");


    private final int code;
    private final String description;

    private ErrorCode(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return code + ": " + description;
    }
}
