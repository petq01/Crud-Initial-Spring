/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.controller;

import com.example.exceptions.EmptyFieldException;
import com.example.exceptions.EmptyFileException;
import com.example.model.Attachment;
import com.example.model.User;
import com.example.model.UserAttachment;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Petya
 */
public interface UserSerivce {

    public List<User> getAllService();

    public User addUserService(User user);

    public User findOneService(Long id);

    public void deleteUserService(User userPrincipal, Long id) throws Exception;

    public User updateUserService(User userPrincipal, User user, Long id) throws EmptyFieldException, Exception;

    public List<User> orderByLastNameAndDateOfBirthService();

    public List<User> searchFirstName(String firstName) throws EmptyFieldException;

    public List<User> searchEmailLikeService(String email) throws EmptyFieldException;

    public UserAttachment createTermsAttachment(
            Long id,
            MultipartFile multipartFile
    ) throws IOException, EmptyFileException;

    public UserAttachment createPolicyAttachment(
            Long id,
            MultipartFile multipartFile
    ) throws IOException, EmptyFileException;

    public Attachment getAttachment(
            Long userId,
            String attId
    );

    public void deleteAttachment(
            Long id,
            String attId
    );

    public void downloadAttachmentData(
            Long userId,
            String attId,
            HttpServletResponse response
    ) throws IOException;

    public <T extends Object> List<User> saveAllUsersFromCSV(byte[] bytes, Class<User> cl);

    public void readWithCSVBeanReader(ByteArrayInputStream bis) throws FileNotFoundException, IOException;

    public List<User> saveList(List<User> userList);

    public void exportCSV() throws IOException;
//////    // public List<User> saveUsersService(List<User> users);
}
