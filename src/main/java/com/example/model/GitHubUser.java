/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author Petya
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubUser {

    private String name;
    private String blog;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBlog() {
        return blog;
    }

    public void setBlog(String blog) {
        this.blog = blog;
    }

    @Override
    public String toString() {
        return "User [name=" + name + ", blog=" + blog + "]";
    }
}
