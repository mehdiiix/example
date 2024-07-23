package internshipmanagement.internshipmanagement.serviceImpl;

import internshipmanagement.internshipmanagement.JWT.CustomerUserDetailsService;
import internshipmanagement.internshipmanagement.JWT.JwtFilter;
import internshipmanagement.internshipmanagement.JWT.JwtUtil;


import internshipmanagement.internshipmanagement.POJO.User;
import internshipmanagement.internshipmanagement.dao.UserDao;
import internshipmanagement.internshipmanagement.service.UserService;

import internshipmanagement.internshipmanagement.wrapper.UserWrapper;


import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    CustomerUserDetailsService customerUserDetailsService;
    @Autowired
    JwtFilter jwtFilter;

    @Override
    public ResponseEntity<List<UserWrapper>> getAllUser() {
        try {
            if (jwtFilter.isAdmin()) {
                return new ResponseEntity<>(userDao.getAllUser(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<List<UserWrapper>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        log.info("Inside signup {}", requestMap);
        if (validateSignUpMap(requestMap)) {
            User user = userDao.findByEmailId(requestMap.get("email"));
            if (Objects.isNull(user)) {
                userDao.save(getUserFromMap(requestMap));
                return new ResponseEntity<>("sucess registrated", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("email already exists", HttpStatus.BAD_REQUEST);
            }

        } else {
            return new ResponseEntity<>("invalid data", HttpStatus.BAD_REQUEST);
        }

    }



    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        log.info("Inside login {}", requestMap);

            try {
                Authentication auth = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(requestMap.get("email"), requestMap.get("password"))
                );
                if (auth.isAuthenticated()) {
                    if (customerUserDetailsService.getUserDetail().getStatus().equalsIgnoreCase("true")) {
                        String jwt = jwtUtil.generateToken(customerUserDetailsService.getUserDetail().getEmail(), customerUserDetailsService.getUserDetail().getRole());
                        return ResponseEntity.ok(jwt);
                    } else {
                        return new ResponseEntity<String>("wait for admin", HttpStatus.BAD_REQUEST);
                    }
                }


            } catch (Exception e) {
                return new ResponseEntity<>("invalid email or password", HttpStatus.BAD_REQUEST);
            }

        return new ResponseEntity<String>("error", HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isUser()) {
                Optional<User> optional = userDao.findById(Integer.parseInt(requestMap.get("id")));
                if (!optional.isEmpty()){
                    userDao.updateStatus(requestMap.get("status"),Integer.parseInt(requestMap.get("id")));
                    return new ResponseEntity<>("user updated succesfully", HttpStatus.OK);

                }else {
                    return new ResponseEntity<>("user doesnt exist", HttpStatus.OK);
                }
            } else {
                return new ResponseEntity<>("unauthorized acess", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> checktoken() {
        return new ResponseEntity<>("true", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> changepassword(Map<String, String> requestMap) {
        try {
            User userObj = userDao.findByEmail(jwtFilter.getCurrentUser());
            if (!userObj.equals(null)){
                if (userObj.getPassword().equals(requestMap.get("OldPassword"))) {
                    userObj.setPassword(requestMap.get("NewPassword"));
                    userDao.save(userObj);
                    return new ResponseEntity<>("passsword updated ", HttpStatus.OK);
                }
                return new ResponseEntity<>("Error passsword ", HttpStatus.BAD_REQUEST);
                }

             return new ResponseEntity<>("Error occurred ", HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>("Error occurred ", HttpStatus.INTERNAL_SERVER_ERROR);
    }




    private boolean validateLoginMap(Map<String,String> requestMap){
        if (requestMap.containsKey("email") && requestMap.containsKey("password")){
            return true;
        }
        return false;
    }

    private boolean validateSignUpMap(Map<String,String> requestMap){
        if (requestMap.containsKey("name") && requestMap.containsKey("contactNumber") && requestMap.containsKey("email") && requestMap.containsKey("password")){
            return true;
        }
        return false;
    }
    private User getUserFromMap(Map<String,String> requestMap){
        User user = new User();
        user.setName(requestMap.get("name"));
        user.setContactnumber(requestMap.get("contactNumber"));
        user.setEmail(requestMap.get("email"));
        user.setPassword(requestMap.get("password"));
        user.setStatus("False");
        user.setRole("user");
        return user;

    }
}
