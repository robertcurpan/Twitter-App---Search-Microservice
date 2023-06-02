package com.twitterapp.searchmicroservice.service;

import com.twitterapp.searchmicroservice.entity.Tweet;
import com.twitterapp.searchmicroservice.exception.exceptions.TweetsByKeywordsNotFoundException;
import com.twitterapp.searchmicroservice.repository.SearchRepository;
import com.twitterapp.searchmicroservice.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


@Service
public class SearchService {

    @Autowired
    private SearchRepository searchRepository;


    public List<Tweet> getTweetsByKeywords(String searchTextKeywords, LocalDateTime timestamp, Integer pageSize) throws TweetsByKeywordsNotFoundException {
        Date dateTimestamp = DateTimeUtil.convertLocalDateTimeToDate(timestamp);
        return searchRepository.getTweetsByKeywords(searchTextKeywords, dateTimestamp, pageSize);
    }
}
