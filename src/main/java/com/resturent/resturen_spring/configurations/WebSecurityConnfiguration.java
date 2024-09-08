package com.resturent.resturen_spring.configurations;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration

public class WebSecurityConnfiguration {

    //@Bean
    //public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
     //   http.csrf(AbstractHttpConfigurer::disable)
      //          .authorizeHttpRequests(request ->request.)
   // }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
