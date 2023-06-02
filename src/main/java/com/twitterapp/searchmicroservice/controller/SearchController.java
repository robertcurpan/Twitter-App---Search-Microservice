package com.twitterapp.searchmicroservice.controller;


import com.twitterapp.searchmicroservice.entity.Tweet;
import com.twitterapp.searchmicroservice.exception.exceptions.TweetsByKeywordsNotFoundException;
import com.twitterapp.searchmicroservice.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/search")
@CrossOrigin("http://localhost:3000")
public class SearchController {

    @Autowired
    private SearchService searchService;


    @GetMapping("")
    public List<Tweet> getTweetsByKeywords(@RequestParam String searchTextKeywords,
                                           @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS") LocalDateTime timestamp,
                                           @RequestParam Integer pageSize) throws TweetsByKeywordsNotFoundException {
        return searchService.getTweetsByKeywords(searchTextKeywords, timestamp, pageSize);
    }

}
