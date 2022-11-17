package com.plannerssystem.utils;

import com.plannerssystem.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //@Query("SELECT u FROM Users u WHERE u.email_address = ?1")
    //public User findByEmail(String email_address);
}
