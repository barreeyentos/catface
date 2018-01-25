package com.barreeyentos.catface.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.barreeyentos.catface.exception.UnreachableImageException;

@ResponseBody
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(UnreachableImageException.class)
    public void handleUnreachableImage() {
        // Nothing to do
    }
}
