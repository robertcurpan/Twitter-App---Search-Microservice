package com.twitterapp.searchmicroservice.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Date;


@Document(indexName = "tweet")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Tweet {

    @Id
    private String tweetId;
    private Integer userId;
    private String username;
    private String text;
    private Date timestamp;
    private Integer likesCount;
    private Integer sharesCount;
}
