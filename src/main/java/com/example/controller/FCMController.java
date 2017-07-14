/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.controller;

import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Petya
 */
@RestController
public class FCMController {

//    @PostMapping(value = {"push/sendFCM"})
//    public void pushFCMNotification(@AuthenticationPrincipal User user, NotificationToken token) throws Exception {
//        FirebasePushNotificationSender androidPush = new FirebasePushNotificationSender();
//        androidPush.setServerApiKey("AAAAHp3F6jw:APA91bEzXEaub1tj417nWDUU0WJwNZ3cZXkXHbGxko7v63CcWSHLxv4J5TuufWLbcZfivXkvDvaLCSISkKBu03MxIe1l110hpp2d28_1rxK88vhOUY8-qfdL1tH56n4KG2GIc97eYLTX");
//        FirebasePushNotificationSender.PushMessage message = FirebasePushNotificationSender.PushMessage.builder()
//                .to("eaHZSlvcu6o:APA91bEPbekIjnLNjnkxVUujbl6nnTmbbg5-YmDNuHDKaBLg4jZhq4GYZkQ_u8_dL2UYbnzWIFi_JvcA3kuombkRwhsGNVGKl1uqobx5CaEJNKOIE0V8vKTov7r5VIS4_ne5V6fU3MwC")
//                .priority(FirebasePushNotificationSender.Priority.normal)
//                .dataItem("message", "hello")
//                .dataItem("sender", "friend")
//                .notification(FirebasePushNotificationSender.NotificationParam.builder().body("Hello").title("New message").build())
//                .build();
//        FirebasePushNotificationSender.PushResponse send = androidPush.send(message);
//        System.out.println(send);
//    }
}
