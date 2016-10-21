/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.repository;

import com.example.model.User;
import com.example.model.UserAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Petya
 */
@Repository
public interface UserAttachmentRepository extends JpaRepository<UserAttachment, Long> {

    UserAttachment findByIdAndUser(String id, User user);
}
