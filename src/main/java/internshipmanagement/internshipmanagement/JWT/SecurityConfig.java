package internshipmanagement.internshipmanagement.JWT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends SecurityConfigurerAdapter {
   @Autowired
   CustomerUserDetailsService customerUserDetailsService;

   @Autowired
   JwtFilter jwtFilter;


   protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       auth.userDetailsService(customerUserDetailsService).passwordEncoder(passwordEncoder());
   }

   @Bean
   public PasswordEncoder passwordEncoder() {
       return new BCryptPasswordEncoder();
   }



   @Bean
   public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
       return config.getAuthenticationManager();
   }
}


   protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
       http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
               .and()
               .csrf()
               .disable()
               .authorizeRequests()
               .requestMatchers("/user/login","/user/signup","/user/forgotpassword")
               .permitAll()
               .and()
               .exceptionHandling()
               .and()
               .sessionManagement()
               .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
       http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

       return http.build();
   }

}

