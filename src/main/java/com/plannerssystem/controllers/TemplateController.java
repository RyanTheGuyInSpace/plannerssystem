package com.plannerssystem.controllers;

import com.plannerssystem.models.*;
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
@RequestMapping("/templates")
public class TemplateController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private RoutineRepository routineRepository;

    @Autowired
    private ItemTemplateRepository templateRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ReminderRepository reminderRepository;

    @Autowired
    private ItemTemplateRepository itemTemplateRepository;

    @Autowired
    private ItemTemplateItemRepository templateItemRepository;

    @GetMapping("")
    public String templatesHome(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        User user = userRepository.findByUserName(currentPrincipalName);

        Set<ItemTemplate> userTemplates = templateRepository.getItemTemplatesByUser(user);

        model.addAttribute("user", user);
        model.addAttribute("templates", userTemplates);

        return "templates/home";
    }

    @GetMapping("/create")
    public String createTemplateModal(Model model) {
        model.addAttribute("itemTemplate", new ItemTemplate());

        return "templates/createTemplateModal";
    }

    @PostMapping("/create")
    public ModelAndView createTemplate(@ModelAttribute("itemTemplate") ItemTemplate itemTemplate) {
        // Finds the user calling the method
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        // Queries the user from the database
        User user = userRepository.findByUserName(currentPrincipalName);

        itemTemplate.setDateCreated(new Date());
        itemTemplate.setUser(user);

        itemTemplateRepository.save(itemTemplate);

        return new ModelAndView("redirect:/templates");
    }

    @GetMapping("/addExistingTaskToTemplate")
    public ModelAndView addExistingTaskToTemplate(long taskID, long templateID) {
        // Finds the user calling the method
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        // Queries the user from the database
        User user = userRepository.findByUserName(currentPrincipalName);

        Task targetTask = taskRepository.getTaskByID(taskID);
        ItemTemplate targetTemplate = templateRepository.getItemTemplateByID(templateID);

        if (targetTask == null || targetTemplate == null
            || !targetTask.getUser().equals(user)
            || !targetTemplate.getUser().equals(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        ItemTemplateItem templateItem = targetTask.createItemTemplateItem();

        templateItem.setUser(user);
        templateItem.setItemTemplate(targetTemplate);

        templateItemRepository.save(templateItem);

        return new ModelAndView("redirect:/templates/edit?templateID=" + targetTemplate.getId());
    }

    @GetMapping("/addExistingRoutineToTemplate")
    public ModelAndView addExistingRoutineToTemplate(long routineID, long templateID) {
        // Finds the user calling the method
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        // Queries the user from the database
        User user = userRepository.findByUserName(currentPrincipalName);

        Routine targetRoutine = routineRepository.getRoutineByID(routineID);
        ItemTemplate targetTemplate = templateRepository.getItemTemplateByID(templateID);

        if (targetRoutine == null || targetTemplate == null
                || !targetRoutine.getUser().equals(user)
                || !targetTemplate.getUser().equals(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        ItemTemplateItem templateItem = targetRoutine.createItemTemplateItem();

        templateItem.setUser(user);
        templateItem.setItemTemplate(targetTemplate);

        templateItemRepository.save(templateItem);

        return new ModelAndView("redirect:/templates/edit?templateID=" + targetTemplate.getId());
    }

    @GetMapping("/addExistingEventToTemplate")
    public ModelAndView addExistingEventToTemplate(long eventID, long templateID) {
        // Finds the user calling the method
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        // Queries the user from the database
        User user = userRepository.findByUserName(currentPrincipalName);

        Event targetEvent = eventRepository.getEventByID(eventID);
        ItemTemplate targetTemplate = templateRepository.getItemTemplateByID(templateID);

        if (targetEvent == null || targetTemplate == null
                || !targetEvent.getUser().equals(user)
                || !targetTemplate.getUser().equals(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        ItemTemplateItem templateItem = targetEvent.createItemTemplateItem();

        templateItem.setUser(user);
        templateItem.setItemTemplate(targetTemplate);

        templateItemRepository.save(templateItem);

        return new ModelAndView("redirect:/templates/edit?templateID=" + targetTemplate.getId());
    }

    @GetMapping("/addExistingReminderToTemplate")
    public ModelAndView addExistingReminderToTemplate(long reminderID, long templateID) {
        // Finds the user calling the method
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        // Queries the user from the database
        User user = userRepository.findByUserName(currentPrincipalName);

        Reminder targetReminder = reminderRepository.getReminderByID(reminderID);
        ItemTemplate targetTemplate = templateRepository.getItemTemplateByID(templateID);

        if (targetReminder == null || targetTemplate == null
                || !targetReminder.getUser().equals(user)
                || !targetTemplate.getUser().equals(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        ItemTemplateItem templateItem = targetReminder.createItemTemplateItem();

        templateItem.setUser(user);
        templateItem.setItemTemplate(targetTemplate);

        templateItemRepository.save(templateItem);

        return new ModelAndView("redirect:/templates/edit?templateID=" + targetTemplate.getId());
    }
}