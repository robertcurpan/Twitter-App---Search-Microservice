package com.twitterapp.searchmicroservice.exception;

import com.twitterapp.searchmicroservice.exception.exceptions.TweetsByKeywordsNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = { TweetsByKeywordsNotFoundException.class })
    public ResponseEntity<ErrorObject> handleTweetsByKeywordsNotFoundException(TweetsByKeywordsNotFoundException ex, WebRequest request) {
        String errorMessage = "There are no tweets in this page!";
        ErrorObject errorObject = new ErrorObject(errorMessage, HttpStatus.NOT_FOUND, LocalDateTime.now());
        return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.NOT_FOUND);
    }
}
