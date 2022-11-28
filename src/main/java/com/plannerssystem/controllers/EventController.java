package com.plannerssystem.controllers;

import com.plannerssystem.models.Event;
import com.plannerssystem.models.Event;
import com.plannerssystem.models.Routine;
import com.plannerssystem.models.User;
import com.plannerssystem.utils.*;
import com.plannerssystem.utils.EventRepository;
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
@RequestMapping("/events")
public class EventController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ItemTemplateRepository itemTemplateRepository;

    @Autowired
    private ItemTemplateItemRepository templateItemRepository;

    public EventController() {
    }

    @GetMapping("")
    public String eventsHome(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        User user = userRepository.findByUserName(currentPrincipalName);

        Set<Event> userEvents = eventRepository.getEventsByUser(user);

        model.addAttribute("user", user);
        model.addAttribute("events", userEvents);
        model.addAttribute("numEvents", userEvents.size());

        return "events/home";
    }

    @GetMapping("/create")
    public String createEventModal(Model model) {
        model.addAttribute("event", new Event());

        return "events/createEventModal";
    }

    @PostMapping("/create")
    public ModelAndView createEvent(@ModelAttribute("event") Event event) {
        // Finds the user calling the method
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        // Queries the user from the database
        User user = userRepository.findByUserName(currentPrincipalName);

        event.setDateCreated(new Date());
        event.setUser(user);

        eventRepository.save(event);

        return new ModelAndView("redirect:/events");
    }

    @GetMapping("/delete")
    public ModelAndView deleteEvent(long eventID) throws ResponseStatusException {
        // Finds the user calling the method
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        // Queries the user from the database
        User user = userRepository.findByUserName(currentPrincipalName);

        Event eventToComplete = eventRepository.getEventByID(eventID);

        // Make sure the user is completing one of their own Events
        if (!eventToComplete.getUser().equals(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        eventToComplete.delete();

        eventRepository.save(eventToComplete);

        return new ModelAndView("redirect:/events");
    }

    @GetMapping("/addEventToTemplate")
    public String addToTemplateModal(long eventID, Model model) {
        // Finds the user calling the method
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        // Queries the user from the database
        User user = userRepository.findByUserName(currentPrincipalName);

        Event eventToAddToTemplate = eventRepository.getEventByID(eventID);

        if (eventToAddToTemplate == null || !eventToAddToTemplate.getUser().equals(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        model.addAttribute("event", eventToAddToTemplate);
        model.addAttribute("templates", itemTemplateRepository.getItemTemplatesByUser(user));

        return "events/addEventToTemplateModal";
    }
}
