/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.controller;

import com.example.multipart.MultipartService;
import com.example.exceptions.EmptyFieldException;
import com.example.exceptions.EmptyFileException;
import com.example.model.Role;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.prefs.CsvPreference;
import com.example.model.User;
import com.example.model.UserAttachment;
import com.example.repository.RoleRepository;
import com.example.repository.UserAttachmentRepository;
import com.example.repository.UserRepository;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;

import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.io.ICsvBeanWriter;

/**
 *
 * @author Petya
 */
@Service
public class UserServiceImpl implements UserSerivce {

    private static final Logger LOG = Logger.getLogger(UserServiceImpl.class.getName());

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserAttachmentRepository userAttachmentRepository;
    @Autowired
    private MultipartService multipartService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private RoleRepository roleRepository;
// Method to send Notifications from server to client end.

    public final static String AUTH_KEY_FCM = " AIzaSyBeNf8ggRtGeYc-89ACNZ88rrlTJnYssw4";
    public final static String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";

// userDeviceIdKey is the device id you will query from your database
    @Override
    public void pushFCMNotification(String userDeviceIdKey) throws Exception {

        String authKey = AUTH_KEY_FCM;   // You FCM AUTH key
        String FMCurl = API_URL_FCM;

        URL url = new URL(FMCurl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setUseCaches(false);
        conn.setDoInput(true);
        conn.setDoOutput(true);

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "key=" + authKey);
        conn.setRequestProperty("Content-Type", "application/json");

        JSONObject json = new JSONObject();
        json.put("to", userDeviceIdKey.trim());
        JSONObject info = new JSONObject();
        info.put("title", "Notificatoin Title");   // Notification title
        info.put("body", "Hello Test notification"); // Notification body
        json.put("notification", info);

        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(json.toString());
        wr.flush();
        conn.getInputStream();
    }

    @Override
    public List<User> getAllService() {
        return userRepository.findAll();
    }

    @Override
    public User addUserService(User user) {
        String password = user.getPassword();

        user.setPassword(passwordEncoder.encode(password));
        user = userRepository.save(user);
        return user;
    }

    @Override
    public User findOneService(Long id) {

        User edit = userRepository.findOne(id);
        if (edit == null) {
            throw new IllegalArgumentException("non existing id");
        }
        return userRepository.findOne(id);
    }

    @Override
    public void deleteUserService(User userPrincipal, Long id) throws Exception {
        if (checkUser(id, userPrincipal)) {
            userRepository.delete(id);
        } else {
            throw new Exception("not permited");
        }

    }

    @Override
    public User updateUserService(User userPrincipal, User user, Long id) throws EmptyFieldException, Exception {
        if (checkUser(id, userPrincipal)) {
            User usr = userRepository.findOne(id);
            String first = usr.getFirstName();

            if (first.isEmpty() == true) {
                throw new EmptyFieldException(first);
            }

            usr.setFirstName(user.getFirstName());
            usr.setLastName(user.getLastName());
            usr.setDateOfBirth(user.getDateOfBirth());
            usr.setEmail(user.getEmail());

            return userRepository.save(usr);
        } else {
            throw new Exception("not permited");
        }
    }

    @Override
    public List<User> orderByLastNameAndDateOfBirthService() {
        return userRepository.findAllByOrderByLastNameAscDateOfBirthAsc();
    }

    @Override
    public List<User> searchFirstName(String firstName) throws EmptyFieldException {

        List<User> edit = userRepository.findByFirstName(firstName);
        if (edit.isEmpty() == true) {
            throw new EmptyFieldException(firstName);
        }
        return userRepository.findByFirstName(firstName);
    }

    @Override
    public List<User> searchEmailLikeService(String email) throws EmptyFieldException {
        List<User> edit = userRepository.findByEmailContainingIgnoreCase(email);
        if (edit.isEmpty() == true) {
            throw new EmptyFieldException(email);
        }
        return userRepository.findByEmailContainingIgnoreCase(email);
    }

    @Override
    public UserAttachment createTermsAttachment(Long id, MultipartFile multipartFile) throws IOException, EmptyFileException {

        User user = userRepository.findOne(id);
        UserAttachment att = new UserAttachment();
        att.setId("terms");
        att.setUser(user);
        att.setFilename(multipartFile.getOriginalFilename());
        att.setContentType(multipartFile.getContentType());
        att.setContent(multipartFile.getBytes());
        att.setDateOfUpload(new Date());

        return userAttachmentRepository.save(att);
    }

    @Override
    public UserAttachment createPolicyAttachment(Long id, MultipartFile multipartFile) throws IOException, EmptyFileException {

        User user = userRepository.findOne(id);
        UserAttachment att = new UserAttachment();
        att.setId("policy");
        att.setUser(user);
        att.setFilename(multipartFile.getOriginalFilename());
        att.setContentType(multipartFile.getContentType());
        att.setContent(multipartFile.getBytes());
        att.setDateOfUpload(new Date());
        att.setVersion(id);

        return userAttachmentRepository.save(att);
    }

    @Override
    public UserAttachment getAttachment(
            Long userId,
            String attId
    ) {

        User user = userRepository.findOne(userId);

        UserAttachment att = userAttachmentRepository.findByIdAndUser(attId, user);

        return att;
    }

    @Override
    public void deleteAttachment(Long id, String attId) {
        User user = userRepository.findOne(id);

        UserAttachment forDeletion = userAttachmentRepository.findByIdAndUser(attId, user);

        userAttachmentRepository.delete(forDeletion);

    }

    @Override
    public void downloadAttachmentData(
            Long userId,
            String attId,
            HttpServletResponse response
    ) throws IOException {

        UserAttachment att = (UserAttachment) getAttachment(userId, attId);

        multipartService.downloadByteArrayData(att.getContent(), att.getContentType(), att.getFilename(), response);

    }

    @Override
    public <T extends Object> List<User> saveAllUsersFromCSV(byte[] bytes, Class<User> cl) {
        ArrayList<User> parsedObjects = new ArrayList();
        try (CsvBeanReader beanReader = new CsvBeanReader(new InputStreamReader(new ByteArrayInputStream(bytes)), CsvPreference.STANDARD_PREFERENCE)) {
            String[] header = beanReader.getHeader(true);
            User parsedObject;
            while ((parsedObject = beanReader.read(cl, header)) != null) {
                Role role = roleRepository.findByName(parsedObject.getRole().toString());
                parsedObject.setRole(role);
                parsedObjects.add(parsedObject);
            }
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        return parsedObjects;
    }

    @Override
    public List<User> saveList(List<User> userList) {
        userList = userRepository.save(userList);
        return userList;
    }

    @Override
    public void exportCSV() throws IOException {
        File file = new File("D:\\mycsv.csv");
        List<User> users = userRepository.findAll();
        try (ICsvBeanWriter beanWriter = new CsvBeanWriter(new FileWriter(file), CsvPreference.STANDARD_PREFERENCE)) {
            String[] header = new String[]{
                "id",
                "firstName",
                "lastName",
                "dateOfBirth",
                "phoneNumber",
                "email",
                "password",
                "role"
            };
            beanWriter.writeHeader(header);
            for (User user : users) {
                beanWriter.write(user, header);
            }
        }

    }

    @Override
    public void readWithCSVBeanReader(ByteArrayInputStream bis) throws FileNotFoundException, IOException {
        try (ICsvBeanReader beanReader = new CsvBeanReader(new InputStreamReader(bis), CsvPreference.STANDARD_PREFERENCE)) {
            String[] header = beanReader.getHeader(true);
            ArrayList<User> parsedObjects = new ArrayList();
            User user;
            while ((user = beanReader.read(User.class, header)) != null) {
                parsedObjects.add(user);

            }
            userRepository.save(parsedObjects);
        }

    }

    private Boolean checkUser(Long id, User currentUser) throws Exception {
        if (currentUser.getId() != id) {
            return false;
        }
        return true;

    }

    public void createFtlTemplate(Long id) throws Exception {

        Configuration cfg = new Configuration();

        // Where do we load the templates from:
        cfg.setClassForTemplateLoading(UserServiceImpl.class, "/templates");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setLocale(Locale.US);
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        Map<String, Object> input = new HashMap<String, Object>();
        input.put("title", "Crud example");
        input.put("user", userRepository.findOne(id));

        input.put("messageSource", messageSource);
        input.put("local", Locale.ENGLISH);

        // http://stackoverflow.com/questions/9605828/email-internationalization-using-velocity-freemarker-templates
        Template template = cfg.getTemplate("helloworld.ftl");
        Writer consoleWriter = new OutputStreamWriter(System.out);
        template.process(input, consoleWriter);

        try ( // For the sake of example, also write output into a file:
                Writer fileWriter = new FileWriter(new File("D:\\output.html"))) {
            template.process(input, fileWriter);
        }
    }

}
