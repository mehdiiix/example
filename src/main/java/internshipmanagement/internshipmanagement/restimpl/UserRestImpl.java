package internshipmanagement.internshipmanagement.restimpl;

import internshipmanagement.internshipmanagement.rest.UserRest;
import internshipmanagement.internshipmanagement.service.UserService;

import internshipmanagement.internshipmanagement.wrapper.UserWrapper;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class UserRestImpl implements UserRest {

    @Autowired
    UserService userService;
    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        try {
            return userService.signUp(requestMap);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>("Error occurred during sign-up", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        try {
            return userService.login(requestMap);
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>("Error occurred during login", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<UserWrapper>> getAllUser() {
        try {
            return userService.getAllUser();
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<List<UserWrapper>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        try {
             return userService.update(requestMap);
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>("Error occurred during update", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> checktoken() {
        try {
            return userService.checktoken();
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>("Error occurred ", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> changepassword(Map<String, String> requestMap) {
        try {
            return userService.changepassword(requestMap);
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>("Error occurred ", HttpStatus.INTERNAL_SERVER_ERROR);
    }


}

