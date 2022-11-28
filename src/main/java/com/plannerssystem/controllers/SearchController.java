package com.plannerssystem.controllers;

import com.plannerssystem.models.*;
import com.plannerssystem.utils.*;
import org.bouncycastle.asn1.cms.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/search")
public class SearchController {
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
    public String search() {
        return "search/home";
    }

    @PostMapping("")
    public String search(String keyword, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User user = userRepository.findByUserName(currentPrincipalName);

        Set<Task> taskMatches = taskRepository.getTasksByKeyword(user, keyword.strip().toLowerCase());
        model.addAttribute("tasks", taskMatches);

        Set<Event> eventMatches = eventRepository.getEventsByKeyword(user, keyword.strip().toLowerCase());
        model.addAttribute("events", eventMatches);

        Set<Routine> routineMatches = routineRepository.getRoutinesByKeyword(user, keyword.strip().toLowerCase());
        model.addAttribute("routines", routineMatches);

        Set<Reminder> reminderMatches = reminderRepository.getRemindersByKeyword(user, keyword.strip().toLowerCase());
        model.addAttribute("reminders", reminderMatches);

        return "search/home";
    }
}