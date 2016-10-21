/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.controller;

import com.example.model.User;
import com.example.controller.AbstractTestRunner;
import com.example.test.ResultSet;
import com.example.repository.UserRepository;
import java.util.Map;
import javax.transaction.Transactional;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 *
 * @author User
 */
@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserControllerTest extends AbstractTestRunner {

    private static ResultSet resultSet;
    private static String request;
    private static Map response;
    private static Long userId;

    @Autowired
    UserRepository userRepository;

    public void postUser() throws JSONException, Exception {

        request
                = "{\n"
                + " \"firstName\": \"petya\"    \n"
                + "}";

        resultSet = perform(MockMvcRequestBuilders.post("/user").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(request))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    //  @Ignore
    @Test
    public void getNameTest() throws JSONException, Exception {

        postUser();
        String firstName;
        firstName = new JSONObject(resultSet.getContentAsString()).getString("firstName");

        Assert.assertEquals("petya", firstName);
    }

    //  @Ignore
    @Test
    public void updateUserTest() throws JSONException, Exception {

        postUser();
        Long id = new JSONObject(resultSet.getContentAsString()).getLong("id");

        request
                = "{"
                + "\"firstName\": \"editedNewName\""
                + "}";

        mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.PUT, "/user/" + id.toString())
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(request))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andReturn();

        User person = userRepository.findOne(id);

        Assert.assertEquals("editedNewName", person.getFirstName());
    }

    // @Ignore
    @Test
    public void deleteUserTest() throws JSONException, Exception {

        postUser();

        String firstName = new JSONObject(resultSet.getContentAsString()).getString("firstName");
        response = resultSet.getObjectFromResponce(Map.class);
        userId = Long.parseLong(response.get("id").toString());

        resultSet = perform(MockMvcRequestBuilders.delete("/user").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(request).param("id", userId.toString()))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        String deletedId = new JSONObject(resultSet.getContentAsString()).getString("id");

        Assert.assertEquals("petya", firstName);
        Assert.assertEquals("null", deletedId);
    }

    //   @Ignore
    @Test
    public void updateNonExistingUserTest() throws Exception {

        String id = "123l4";
        request
                = "{\n"
                + "\"firstName\": \"petya\"\n"
                + "}";

        resultSet = perform(MockMvcRequestBuilders.put("/user/" + id).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(request))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError());

        Assert.assertEquals("1002", resultSet.getErrorCode());
    }
}
