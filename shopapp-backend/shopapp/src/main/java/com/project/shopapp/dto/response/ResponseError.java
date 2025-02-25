package com.project.shopapp.dto.response;

import org.springframework.http.HttpStatus;

public class ResponseError extends ResponseData {

    public ResponseError(HttpStatus status, String message) {
        super(status, message);
    }
}
