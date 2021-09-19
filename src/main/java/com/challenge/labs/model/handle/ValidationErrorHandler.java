package com.challenge.labs.model.handle;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ValidationErrorHandler extends StandardErrorHandler {

    private List<FieldMessageErrorHandler> fieldErrors;

    public ValidationErrorHandler() {
        this.fieldErrors = new ArrayList<>();
    }

    public ValidationErrorHandler(LocalDateTime timeStamp, Integer status, String error, String message, String path) {
        super(timeStamp, status, error, message, path);
        this.fieldErrors = new ArrayList<>();
    }

    public void setFieldErrors(List<FieldMessageErrorHandler> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }

    public void addFieldMessageErrorHandler(String fieldName, String message) {
        fieldErrors.add(FieldMessageErrorHandler.builder()
                .fieldName(fieldName)
                .message(message)
                .build());
    }
}