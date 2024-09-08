package com.resturent.resturen_spring.repositories;

import com.resturent.resturen_spring.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);


}
