package com.resturent.resturen_spring.controllers;

import com.resturent.resturen_spring.dtos.SignupRequest;
import com.resturent.resturen_spring.dtos.UserDto;
import com.resturent.resturen_spring.service.auth.AutoService;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AutoService autoService;

    public AuthController(AutoService autoService) {
        this.autoService = autoService;
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest){
      UserDto createdUserDto= autoService.createUser(signupRequest);

      if (createdUserDto==null){
          return new ResponseEntity<>("user not created.come agin later", HttpStatus.BAD_REQUEST);
      }
        return new ResponseEntity<>(createdUserDto,HttpStatus.CREATED);
    }
    //public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest){
        
    //}
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody SignupRequest signupRequest) {
        try {
            UserDto userDto = autoService.login(signupRequest);
            return ResponseEntity.ok(userDto);
        } catch (Exception e) {
            return ResponseEntity.status(401).body(null); // Unauthorized if login fails
        }
    }
}
