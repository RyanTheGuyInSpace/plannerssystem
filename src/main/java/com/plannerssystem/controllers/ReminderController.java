package com.plannerssystem.controllers;

import com.plannerssystem.models.Reminder;
import com.plannerssystem.models.User;
import com.plannerssystem.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.Set;

@Controller
@RequestMapping("/reminders")
public class ReminderController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ReminderRepository reminderRepository;

    @GetMapping("")
    public String remindersHome(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        User user = userRepository.findByUserName(currentPrincipalName);

        Set<Reminder> userReminders = reminderRepository.getRemindersByUser(user);

        model.addAttribute("user", user);
        model.addAttribute("reminders", userReminders);
        model.addAttribute("numReminders", userReminders.size());

        return "reminders/home";
    }

    @GetMapping("/create")
    public String createReminderModal(Model model) {
        model.addAttribute("reminder", new Reminder());

        return "reminders/createReminderModal";
    }

    @PostMapping("/create")
    public ModelAndView createReminder(@ModelAttribute("reminder") Reminder reminder) {
        // Finds the user calling the method
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        // Queries the user from the database
        User user = userRepository.findByUserName(currentPrincipalName);

        reminder.setDateCreated(new Date());
        reminder.setUser(user);

        reminderRepository.save(reminder);

        return new ModelAndView("redirect:/reminders");
    }

    @GetMapping("/edit")
    public String editReminder(long reminderID, Model model) {
        // Finds the user calling the method
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        // Queries the user from the database
        User user = userRepository.findByUserName(currentPrincipalName);

        Reminder targetReminder = reminderRepository.getReminderByID(reminderID);

        if (targetReminder == null || !targetReminder.getUser().equals(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        model.addAttribute("reminder", targetReminder);

        return "reminders/edit";
    }

    @PostMapping("/edit")
    public ModelAndView editReminder(long reminderID, Reminder reminder, Model model) {
        // Finds the user calling the method
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        // Queries the user from the database
        User user = userRepository.findByUserName(currentPrincipalName);

        Reminder targetReminder = reminderRepository.getReminderByID(reminderID);

        if (targetReminder == null || !targetReminder.getUser().equals(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        targetReminder.setName(reminder.getName());
        targetReminder.setDescription(reminder.getDescription());

        reminderRepository.save(targetReminder);

        return new ModelAndView("redirect:/reminders/edit?reminderID=" + reminderID);
    }
}
