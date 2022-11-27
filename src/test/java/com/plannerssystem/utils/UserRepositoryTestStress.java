package com.plannerssystem.utils;


import java.util.Random;
import com.plannerssystem.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTestStress {

    @Autowired
    private TestEntityManager entityManagerFactory;

    @Autowired
    private UserRepository repo;

    @Test
    public void testCreateUsers() {

        int numUsers = 100;
        int ranNum;
        int ranNum2;
        boolean userEquals = false;
        String[] strArr = new String[5];
        Random ranObj = new Random();

        //fill the string array with random index
        for(int i = 0; i < 5; i++) {

            ranNum = ranObj.nextInt(5);
            switch(ranNum) {
                case 0:
                    strArr[i] = "Bob";
                    break;
                case 1:
                    strArr[i] = "Smith";
                    break;
                case 2:
                    strArr[i] = "John";
                    break;
                case 3:
                    strArr[i] = "Ana";
                    break;
                case 4:
                    strArr[i] = "Aidan";
                    break;
            }
        }

        //generate random users for numUsers times
        for (int i = 0; i < numUsers; i++) {

            User user = new User();
            ranNum = ranObj.nextInt(5);
            ranNum2 = ranObj.nextInt(5);

            switch(ranNum) {
                case 0:
                    user.setEmailAddress("test.user" + i + "@gmail.com");
                    break;
                case 1:
                    user.setEmailAddress("test.user" + i + "@yahoo.com");
                    break;
                case 2:
                    user.setEmailAddress("test.user" + i + "@hotmail.com");
                    break;
                case 3:
                    user.setEmailAddress("test.user" + i + "@outlook.com");
                    break;
                case 4:
                    user.setEmailAddress("test.user" + i + "@Knights.ucf.edu");
                    break;
            }

            user.setPassword("testPassword" + i);
            user.setUserName("testuser" + i);
            user.setFirstName(strArr[ranNum]);
            user.setLastName(strArr[ranNum2]);

            User savedUser = repo.save(user);

            User existingUser = entityManagerFactory.find(User.class, savedUser.getId());

            userEquals = user.getEmailAddress().equals(existingUser.getEmailAddress());

            //exit if the created user is not found in the database
            if(!userEquals) {
                break;
            }
        }

        //check that all users were eventually put in the database
        assertTrue(userEquals);
    }
}
