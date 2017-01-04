/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example;

import com.example.controller.GitHubLookupService;
import com.example.model.GitHubUser;
import java.util.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 *
 * @author Petya
 */
@Component
public class AppRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(AppRunner.class);

    private final GitHubLookupService gitHubLookupService;

    public AppRunner(GitHubLookupService gitHubLookupService) {
        this.gitHubLookupService = gitHubLookupService;
    }

    @Override
    public void run(String... args) throws Exception {
        // Start the clock
        long start = System.currentTimeMillis();

        // Kick of multiple, asynchronous lookups
        Future<GitHubUser> page1 = gitHubLookupService.findUser("PivotalSoftware");
        Future<GitHubUser> page2 = gitHubLookupService.findUser("CloudFoundry");
        Future<GitHubUser> page3 = gitHubLookupService.findUser("Spring-Projects");
        Future<GitHubUser> page4 = gitHubLookupService.findUser("petq01");

        // Wait until they are all done
        while (!(page1.isDone() && page2.isDone() && page3.isDone() && page4.isDone())) {
            Thread.sleep(10); //10-millisecond pause between each check
        }

        // Print results, including elapsed time
        logger.info("Elapsed time: " + (System.currentTimeMillis() - start));
        logger.info("--> " + page1.get());
        logger.info("--> " + page2.get());
        logger.info("--> " + page3.get());
        logger.info("--> " + page4.get());
    }

}
