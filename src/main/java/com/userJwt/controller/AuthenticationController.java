package com.userJwt.controller;

import com.userJwt.Model.AuthenticationResponse;
import com.userJwt.db.entity.User;
import com.userJwt.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    //Api to register user

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody User user){

        return ResponseEntity.ok(authenticationService.register(user));
    }

    //Api to login api
    @GetMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody User user){

        return  ResponseEntity.ok(authenticationService.authenticate(user));
    }

    //api to test authentication i.e. test jwt to request data from server
    @GetMapping("/test")
    public String test(){
        return  "akash    ";
    }
    ////api to test authorization i.e. test jwt to request data from server for admin

}
