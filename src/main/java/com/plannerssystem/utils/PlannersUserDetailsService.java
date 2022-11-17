package com.plannerssystem.utils;

import com.plannerssystem.models.PlannersUserDetails;
import com.plannerssystem.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PlannersUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //User user = userRepository.findByEmail(username);

        User user = new User();

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new PlannersUserDetails(user);
    }
}

