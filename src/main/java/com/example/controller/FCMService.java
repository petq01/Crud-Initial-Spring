/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.controller;

import com.example.config.FirebaseServerProperties;
import com.example.model.NotificationToken;
import com.example.model.User;
import com.example.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Petya
 */
@Service
public class FCMService {

    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private FirebaseServerProperties firebaseServerProperties;
//    @Autowired
//    private RestTemplate restTemplate = new RestTemplate();

    public NotificationToken addToken(NotificationToken token, User user) {

        token.setUser(user);
        return tokenRepository.save(token);

    }

    public NotificationToken findToken(User user) {
        return tokenRepository.findByUser(user);
    }

//    public void makeRequest() throws ProtocolException, MalformedURLException, IOException {
//        URL url = new URL("https://fcm.googleapis.com/fcm/send");
//
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        conn.setUseCaches(false);
//        conn.setDoInput(true);
//        conn.setDoOutput(true);
//
//        conn.setRequestMethod("POST");
//        conn.setRequestProperty("Authorization", "key=" + firebaseServerProperties.getServerkey()); //server key
//        conn.setRequestProperty("Content-Type", "application/json");
//
//        JSONObject json = new JSONObject();
//        json.put("to", "eaHZSlvcu6o:APA91bEPbekIjnLNjnkxVUujbl6nnTmbbg5-YmDNuHDKaBLg4jZhq4GYZkQ_u8_dL2UYbnzWIFi_JvcA3kuombkRwhsGNVGKl1uqobx5CaEJNKOIE0V8vKTov7r5VIS4_ne5V6fU3MwC");
//        JSONObject info = new JSONObject();
//        info.put("title", "Notificatoin Title"); // Notification title
//        info.put("body", "Hello Test notification "); // Notification body
//        json.put("notification", info);
//
//        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
//        wr.write(json.toString());
//
//        wr.flush();
//        conn.getInputStream();
//    }
//     public NotificationToken findToken(User user) {
//        return repository.findByUser(user);
//    }
//    public void sendNotificationToUser(User user, String bodyRequest, String titleRequest) {
//        NotificationToken token = this.findToken(user);
//        if (token != null) {
//            PushMessage message = PushMessage.builder()
//                    .to(token.getDeviceToken())
//                    .priority(Priority.normal)
//                    .notification(NotificationParam.builder().body(bodyRequest).title(titleRequest).build())
//                    .build();
//            this.send(message);
//        }
//    }
//    public PushResponse send(PushMessage message) {
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("Authorization", "key=" + properties.getServerkey());
//        HttpEntity<PushMessage> entity = new HttpEntity<>(message, headers);
//        ResponseEntity<PushResponse> exchange = restTemplate.exchange(properties.getUrl(), HttpMethod.POST, entity, PushResponse.class);
//        return exchange.getBody();
//    }
}
