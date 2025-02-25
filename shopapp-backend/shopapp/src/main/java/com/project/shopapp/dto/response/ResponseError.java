package com.project.shopapp.dto.response;

import org.springframework.http.HttpStatus;

public class ResponseError extends ResponseData {

    public ResponseError(int status, String message) {
        super(status, message);
    }
}
