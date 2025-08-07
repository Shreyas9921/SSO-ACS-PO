package com.acs.Test.exception;

import io.swagger.annotations.Api;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class ApiError {
    private String message;

    public ApiError(String message) {
        this.message = message;
    }
}
