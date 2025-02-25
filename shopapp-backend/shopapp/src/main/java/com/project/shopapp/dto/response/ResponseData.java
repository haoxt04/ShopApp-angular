package com.project.shopapp.dto.response;

import org.springframework.http.HttpStatus;

public class ResponseData<T> {
    private final HttpStatus status;
    private final String message;
    private T data;

    // GET, POST
    public ResponseData(HttpStatus status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
    // PUT, PATCH, DELETE
    public ResponseData(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
