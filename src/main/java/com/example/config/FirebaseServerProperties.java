package com.example.config;


import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *
 * @author Petya
 */
@ConfigurationProperties(prefix = "firebase")
public class FirebaseServerProperties {

    private String serverkey;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    private String url;

    public void setServerkey(String serverkey) {
        this.serverkey = serverkey;
    }

    public String getServerkey() {
        return serverkey;
    }
}
