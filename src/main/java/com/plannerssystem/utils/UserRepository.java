package com.plannerssystem.utils;

import com.plannerssystem.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.emailAddress = ?1")
    public User findByEmail(String email_address);

    @Query("SELECT u FROM User u WHERE u.userName = ?1")
    public User findByUserName(String userName);
}
