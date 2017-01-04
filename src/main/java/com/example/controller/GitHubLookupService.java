/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.controller;

import com.example.model.GitHubUser;
import java.util.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Petya
 */
@Service
public class GitHubLookupService {

    private static final Logger logger = LoggerFactory.getLogger(GitHubLookupService.class);

    private final RestTemplate restTemplate;

    public GitHubLookupService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Async
    public Future<GitHubUser> findUser(String user) throws InterruptedException {
        logger.info("Looking up " + user);
        String url = String.format("https://api.github.com/users/%s", user);
        GitHubUser results = restTemplate.getForObject(url, GitHubUser.class);
        // Artificial delay of 1s for demonstration purposes
        Thread.sleep(1000L);
        return new AsyncResult<>(results);
    }
}
