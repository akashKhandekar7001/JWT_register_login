package com.userJwt.service.userServiceImpl;

import com.userJwt.db.entity.User;
import com.userJwt.db.reposetory.UserRepo;
import com.userJwt.exceptionHandlter.ResourceNotFoundException;
import com.userJwt.service.userSerice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepo userRepo;

    public User getUserDetails(Integer id){
        return userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + id + " not found"));
    }

    public List<User> getAllUserDetails(){
        return userRepo.findAll();
    }


}
