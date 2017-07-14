/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.repository;

import com.example.model.NotificationToken;
import com.example.model.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Petya
 */
@Repository
public interface TokenRepository extends JpaRepository<NotificationToken, Long> {

    NotificationToken findOneByIdAndUser(Long id, User user);

    List<NotificationToken> findAllByUser(User user);

    NotificationToken findByUser(User user);
}
