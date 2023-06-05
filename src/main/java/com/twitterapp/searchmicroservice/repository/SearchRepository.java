package com.twitterapp.searchmicroservice.repository;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.twitterapp.searchmicroservice.entity.Tweet;
import com.twitterapp.searchmicroservice.exception.exceptions.TweetsByKeywordsNotFoundException;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class SearchRepository {

    public static final String INDEX_NAME = "indexul_meu";
    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private RestClients.ElasticsearchRestClient elasticsearchRestClient;


    public List<Tweet> getTweetsByKeywords(String searchTextKeywords, Date timestamp, Integer pageSize) throws TweetsByKeywordsNotFoundException {
        System.out.println("x");
        QueryBuilder queryBuilder = QueryBuilders
                .boolQuery()
                .must(QueryBuilders.matchQuery("text", searchTextKeywords).operator(Operator.AND))
                .filter(QueryBuilders.rangeQuery("timestamp").lt(timestamp));

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
                .size(pageSize)
                .query(queryBuilder)
                .sort(SortBuilders.fieldSort("timestamp").order(SortOrder.DESC));

        SearchRequest searchRequest = new SearchRequest(INDEX_NAME);
        searchRequest.source(searchSourceBuilder);

        try {
            SearchResponse searchResponse = elasticsearchRestClient.rest().search(searchRequest, RequestOptions.DEFAULT);
            SearchHit[] searchHits = searchResponse.getHits().getHits();
            List<Tweet> tweets = new ArrayList<>();
            for(SearchHit searchHit : searchHits) {
                Map<String, Object> tweetFieldsMap = searchHit.getSourceAsMap();
                String tweetId = (String) tweetFieldsMap.get("id");
                Integer userId = (Integer) tweetFieldsMap.get("userId");
                String username = (String) tweetFieldsMap.get("username");
                String text = (String) tweetFieldsMap.get("text");
                Integer likesCount = (Integer) tweetFieldsMap.get("likesCount");
                Integer sharesCount = (Integer) tweetFieldsMap.get("sharesCount");

                String timestampString = (String) tweetFieldsMap.get("timestamp");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX");
                LocalDateTime dateTime = LocalDateTime.parse(timestampString, formatter);
                Date tweetTimestamp = Date.from(dateTime.atZone(ZoneOffset.UTC).toInstant());

                Tweet tweet = new Tweet(tweetId, userId, username, text, tweetTimestamp, likesCount, sharesCount);
                tweets.add(tweet);
            }

            if(tweets.size() == 0) {
                throw new TweetsByKeywordsNotFoundException();
            }

            return tweets;
        } catch (IOException exception) {
            throw new RuntimeException();
        }

    }
}
