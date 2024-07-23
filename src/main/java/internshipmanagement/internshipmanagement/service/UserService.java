package internshipmanagement.internshipmanagement.service;

import internshipmanagement.internshipmanagement.wrapper.UserWrapper;


import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface UserService {


    ResponseEntity<List<UserWrapper>> getAllUser();

    ResponseEntity<String> signUp(Map<String, String> requestMap);

    ResponseEntity<String> login(Map<String, String> requestMap);

    ResponseEntity<String> update(Map<String, String> requestMap);

    ResponseEntity<String> checktoken();

    ResponseEntity<String> changepassword(Map<String, String> requestMap);


}
