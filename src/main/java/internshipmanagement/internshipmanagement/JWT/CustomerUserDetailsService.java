 package internshipmanagement.internshipmanagement.JWT;

import internshipmanagement.internshipmanagement.POJO.User;
import internshipmanagement.internshipmanagement.dao.UserDao;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Slf4j
@Service
public class CustomerUserDetailsService implements UserDetailsService {

   @Autowired
   UserDao userDao;
   @Getter
   private internshipmanagement.internshipmanagement.POJO.User userDetail;
   @Autowired
   private PasswordEncoder passwordEncoder;

   @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       log.info("Inside loadUserbyUsername {}",username);
        userDetail = userDao.findByEmailId(username);
       if (!Objects.isNull(userDetail)) {
           return new org.springframework.security.core.userdetails.User(userDetail.getEmail(), userDetail.getPassword(), new ArrayList<>());
       }
       throw new UsernameNotFoundException("User not found");
   }

   public String getEmail(String email) {
       User userDetail = userDao.findByEmailId(email);
       if (userDetail != null) {
           return userDetail.getEmail();
       }
       return null;
   }

   public String getRole(String email) {
       User userDetail = userDao.findByEmailId(email);
       if (userDetail != null) {
           return userDetail.getRole();
       }
       return null;
   }
}
