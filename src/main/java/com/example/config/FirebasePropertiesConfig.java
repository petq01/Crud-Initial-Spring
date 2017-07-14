/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Petya
 */
@Configuration
public class FirebasePropertiesConfig {

    @Bean
    public FirebaseServerProperties firebaseServerProperties() {
        return new FirebaseServerProperties();
    }
}
