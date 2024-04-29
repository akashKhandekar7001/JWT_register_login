package com.userJwt.db.reposetory;


import org.springframework.data.jpa.repository.JpaRepository;

import com.userJwt.db.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

    Optional<User> findByUserName(String userName);

}
