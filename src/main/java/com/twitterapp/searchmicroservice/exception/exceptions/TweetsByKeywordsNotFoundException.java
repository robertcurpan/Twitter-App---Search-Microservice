package com.twitterapp.searchmicroservice.exception.exceptions;

public class TweetsByKeywordsNotFoundException extends Exception {
    public TweetsByKeywordsNotFoundException() {
        super();
    }

    public TweetsByKeywordsNotFoundException(String message) {
        super(message);
    }
}
