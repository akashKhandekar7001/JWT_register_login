package com.userJwt.service;

import com.userJwt.db.entity.User;
import com.userJwt.Model.AuthenticationResponse;
import com.userJwt.db.reposetory.UserRepo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final  JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepo userRepo, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    //logic for save user data and return jwt token
    public AuthenticationResponse register(User request){
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUserName(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        userRepo.save(user);
        String token = jwtService.gernateTocken(user);
        return new AuthenticationResponse(token);
    }
    //logic to authenticate user and generate token
    public  AuthenticationResponse authenticate(User requUser){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requUser.getUsername(),
                        requUser.getPassword()
                )
        );
        User user = userRepo.findByUserName(requUser.getUsername()).orElseThrow();
        String token  = jwtService.gernateTocken(user);
        return new AuthenticationResponse(token);
    }
}
