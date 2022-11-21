package com.plannerssystem.utils;

import com.plannerssystem.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {

    @Autowired
    private TestEntityManager entityManagerFactory;

    @Autowired
    private UserRepository repo;

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setEmailAddress("test.user2@Knights.ucf.edu");
        user.setPassword("testPassword2");
        user.setUserName("testuser2");
        user.setFirstName("George");
        user.setLastName("Washington");

        User savedUser = repo.save(user);

        User existingUser = entityManagerFactory.find(User.class, savedUser.getId());

        assertThat(user.getEmailAddress().equals(existingUser.getEmailAddress()));
    }

}
