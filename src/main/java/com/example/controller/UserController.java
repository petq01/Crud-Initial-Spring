package com.example.controller;

import com.example.aspect.ValidateUser;
import com.example.exceptions.EmptyFieldException;
import com.example.exceptions.EmptyFileException;
import com.example.model.User;
import com.example.model.UserAttachment;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Petya
 */
@RestController
public class UserController {

    @Autowired
    UserSerivce userService;

    @PreAuthorize(value = "hasAuthority('PERM_READ_USERS')")
    @RequestMapping(value = "/user", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ValidateUser
    public @ResponseBody
    List<User> getAll() {

        return userService.getAllService();

    }

    @RequestMapping(value = "/pushAll", method = RequestMethod.POST)
    public void pushAll(@RequestParam(value = "userDeviceIdKey") String userDeviceIdKey) throws Exception {

        userService.pushFCMNotification(userDeviceIdKey);
    }

    @RequestMapping(value = "user/findByFirstName/{firstname}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<User> searchFirstName(@PathVariable(value = "firstname") String firstName) throws EmptyFieldException {

        return userService.searchFirstName(firstName);
    }

    @RequestMapping(value = "user/searchLike/{email}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<User> searchEmailLike(@PathVariable(value = "email") String email) throws EmptyFieldException {

        return userService.searchEmailLikeService(email);
    }

    //query that sorts the database by last name and date of birth.
    @RequestMapping(value = "user/orderByLastNameAndDateOfBirth", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<User> orderByLastNameAndDateOfBirth() {

        return userService.orderByLastNameAndDateOfBirthService();
    }

    @PreAuthorize(value = "hasAuthority('PERM_CREATE_USERS')")
    @RequestMapping(value = "/user", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    User addUsers(@RequestBody User user) {

        return userService.addUserService(user);
    }

    @PreAuthorize(value = "hasAuthority('PERM_UPDATE_USERS')")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT /*, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE */)
    @ResponseBody
    public User updateUser(@AuthenticationPrincipal User userPrincipal, @RequestBody User user, @PathVariable(value = "id") Long id) throws Exception {

        return userService.updateUserService(userPrincipal, user, id);
    }

    @PreAuthorize("hasAuthority('PERM_DELETE_USERS')")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteUser(@AuthenticationPrincipal User userPrincipal, @PathVariable(value = "id") Long id) throws Exception {

        userService.deleteUserService(userPrincipal, id);

        ResponseEntity e = new ResponseEntity(HttpStatus.OK);
        return e;

    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ValidateUser
    public @ResponseBody
    User findOne(@PathVariable(value = "id") Long id) {

        return userService.findOneService(id);
    }

    @RequestMapping(value = "/user/{id}/attachment/terms", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, String> createUserTermsAttachment(
            @PathVariable("id") Long userId,
            @RequestParam(value = "file") MultipartFile multipartFile
    ) throws IOException, EmptyFileException {
        HashMap<String, String> responce = new HashMap();
        responce.put("attachmentId", userService.createTermsAttachment(userId, multipartFile).getId());

        return responce;
    }

    @RequestMapping(value = "/user/{id}/attachment/policy", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, String> createUserPolicyAttachment(
            @PathVariable("id") Long userId,
            @RequestParam(value = "file") MultipartFile multipartFile
    ) throws IOException, EmptyFileException {
        HashMap<String, String> responce = new HashMap();
        responce.put("attachmentId", userService.createPolicyAttachment(userId, multipartFile).getId());

        return responce;
    }

    /**
     * <p>
     * View an attachment with given ID for user with given ID
     *
     * @param userId The ID of the user for whom the Attachment is returned
     * @param attId The ID of the Attachment to be returned
     * @return UserAttachment
     */
    @RequestMapping(value = "/user/{id}/attachment/{attId}", method = RequestMethod.GET)
    public UserAttachment viewUserAttachment(
            @PathVariable("id") Long userId,
            @PathVariable("attId") String attId
    ) {
        return (UserAttachment) userService.getAttachment(userId, attId);

    }

    @RequestMapping(value = "/user/{id}/attachment/{attId}", method = RequestMethod.DELETE)
    public void deleteUserAttachment(
            @PathVariable("id") Long userId,
            @PathVariable("attId") String attId
    ) {
        userService.deleteAttachment(userId, attId);
    }

    @RequestMapping(value = "/user/{id}/attachment/{attId}/file", method = RequestMethod.GET)
    public void downloadUserAttachmentData(
            @PathVariable("id") Long userId,
            @PathVariable("attId") String attId,
            HttpServletResponse response
    ) throws IOException {
        userService.downloadAttachmentData(userId, attId, response);
    }

    @RequestMapping(value = "/user/csv", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createCSVImport(
            @RequestParam(value = "file") MultipartFile multipartFile
    ) throws IOException {

        List<User> result = userService.saveAllUsersFromCSV(multipartFile.getBytes(), User.class);
        userService.saveList(result);
    }

    @RequestMapping(value = "/user/csv/read", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void readWithCSVBeanReader(
            @RequestParam(value = "file") MultipartFile multipartFile
    ) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(multipartFile.getBytes());
        userService.readWithCSVBeanReader(bis);

    }

    @RequestMapping(value = "user/csv", method = RequestMethod.GET)
    public void exportCSV() throws IOException {
        userService.exportCSV();
    }

//    @RequestMapping(value = "user/{id}/createTemplate", method = RequestMethod.GET)
//    public @ResponseBody
//    void createFtlTemplate(@PathVariable(value = "id") Long id) throws Exception {
//        userService.createFtlTemplate(id);
//    }
}
