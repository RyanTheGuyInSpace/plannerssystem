package com.plannerssystem.controllers;

import com.plannerssystem.models.User;
import com.plannerssystem.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
public class AppController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private RoutineRepository routineRepository;

    @Autowired
    private ReminderRepository reminderRepository;

    @GetMapping("")
    public String viewHomePage() {
        return "index";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());

        return "signup_form";
    }

    @PostMapping("/process_register")
    public String processRegister(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepository.save(user);

        return "register_success";
    }

    @GetMapping("/home")
    public String listUsers(Model model) {
        // Finds the user calling the method
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        // Queries the user from the database
        User user = userRepository.findByUserName(currentPrincipalName);

        SimpleDateFormat dateFormatter = new SimpleDateFormat("MM-dd-yyyy");

        model.addAttribute("tasks", taskRepository.getTasksByUser(user));
        model.addAttribute("events", eventRepository.getEventsByUser(user));
        model.addAttribute("routines", routineRepository.getRoutinesByUser(user));
        model.addAttribute("reminders", reminderRepository.getRemindersByUser(user));

        model.addAttribute("dateFormatter", dateFormatter);

        return "home";
    }
}
