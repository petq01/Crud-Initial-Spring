/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.aspect;

import com.example.model.User;
import com.example.repository.UserRepository;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

/**
 *
 * @author Petya
 */
@Aspect
@Component
@EnableAspectJAutoProxy
public class UserAspect {

    @Autowired
    UserRepository userRepo;

    @Before("@annotation(com.example.aspect.ValidateUser)&& args(id)")
    public void validateUser(Long id) throws Exception {
        User usr = userRepo.findOne(id);
        if (usr == null) {
            throw new Exception("Illegal number id");
        }
    }
}
