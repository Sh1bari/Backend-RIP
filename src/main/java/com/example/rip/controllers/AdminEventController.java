package com.example.rip.controllers;

import com.example.rip.models.dtos.response.EventRes;
import com.example.rip.models.dtos.response.MenuElement;
import com.example.rip.models.enums.EventState;
import com.example.rip.services.EventService;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
@Controller
@RequiredArgsConstructor
public class AdminEventController {
    private final EventService eventService;
    @GetMapping("/admin/events")
    public String getEvents(Model model,
                            @RequestParam(name = "eventName", required = false, defaultValue = "") String name,
                            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                            @RequestParam(name = "status", required = false, defaultValue = "ACTIVE") EventState status) {
        Page<EventRes> res = eventService.getEventsByPageFiltered(name, status, page);
        model.addAttribute("events", res.getContent());

        List<MenuElement> menuElements = Arrays.asList(
                new MenuElement("Мероприятия", "http://localhost:8082/api/admin/events")
        );
        model.addAttribute("menuElements", menuElements);
        model.addAttribute("defaultName", name);

        return "adminEventPage";
    }
}
