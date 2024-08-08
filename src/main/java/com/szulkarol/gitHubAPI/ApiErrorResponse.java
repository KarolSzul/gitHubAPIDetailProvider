package com.szulkarol.gitHubAPI;

public class ApiErrorResponse {

    private final int code;
    private final String message;

    public ApiErrorResponse(int code, String message) {
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
