package com.example.rip.controllers;

import com.example.rip.models.dtos.response.EventRes;
import com.example.rip.models.dtos.response.Holiday;
import com.example.rip.models.dtos.response.MenuElement;
import com.example.rip.models.enums.EventState;
import com.example.rip.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping("/events")
    public String getEvents(Model model,
                            @RequestParam(name = "eventName", required = false, defaultValue = "") String name,
                            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                            @RequestParam(name = "status", required = false, defaultValue = "ACTIVE")EventState status) {
        Page<EventRes> res = eventService.getEventsByPageFiltered(name, status, page);
        model.addAttribute("events", res.getContent());

        List<MenuElement> menuElements = Arrays.asList(
                new MenuElement("Мероприятия", "http://localhost:8082/api/events")
        );
        model.addAttribute("menuElements", menuElements);
        model.addAttribute("defaultName", name);

        return "mainPage";
    }

    @GetMapping("/events/archive")
    public String getArchivedEvents(Model model,
                            @RequestParam(name = "eventName", required = false, defaultValue = "") String name,
                            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                            @RequestParam(name = "status", required = false, defaultValue = "ARCHIVED")EventState status) {
        Page<EventRes> res = eventService.getEventsByPageFiltered(name, status, page);
        model.addAttribute("events", res.getContent());

        List<MenuElement> menuElements = Arrays.asList(
                new MenuElement("Мероприятия", "http://localhost:8082/api/events"),
                new MenuElement("Архив", "http://localhost:8082/api/events/archive")
        );
        model.addAttribute("menuElements", menuElements);
        model.addAttribute("defaultName", name);

        return "archiveMainPage";
    }

    @GetMapping("/event/{id}")
    public String getEvent(Model model, @PathVariable(name = "id") Integer id) {
        EventRes res = eventService.getEventById(id);
        List<MenuElement> menuElements = Arrays.asList(
                new MenuElement("Мероприятия", "http://localhost:8082/api/events"),
                new MenuElement(res.getName(), "http://localhost:8082/api/event/" + id)
        );

        model.addAttribute("menuElements", menuElements);

        model.addAttribute("selectedEvent", res);
        return "event";
    }

    @GetMapping("/about")
    public String getAbout(Model model) {
        List<MenuElement> menuElements = Arrays.asList(
                new MenuElement("Мероприятия", "http://localhost:8082/api/events"),
                new MenuElement("О нас", "")
        );

        model.addAttribute("menuElements", menuElements);
        return "about";
    }

    private List<Holiday> createSampleHolidays() {
        List<Holiday> holidays = new ArrayList<>();

        holidays.add(new Holiday(1,"Christmas", "Celebration of joy and happiness", "25th December", "https://cdn.7days.ru/pic/08e/984797/1462728/86.jpg", 30, 10));
        holidays.add(new Holiday(2,"New Year", "Welcoming the upcoming year", "1st January", "https://sakhalife.ru/wp-content/uploads/2023/01/novyj-god.png", 40, 2));
        holidays.add(new Holiday(3,"Christmas", "Celebration of joy and happiness", "25th December", "https://cdn.7days.ru/pic/08e/984797/1462728/86.jpg", 30, 12));
        holidays.add(new Holiday(4,"New Year", "Welcoming the uйц уйцуйц йцуйцуйУУУУ УУУУУУУУУУ УУ УУУУУУУцйуса3уаЦ АУС цук ываыpcoming year", "1st January", "https://cdn.7days.ru/pic/08e/984797/1462728/86.jpg", 90, 33));
        holidays.add(new Holiday(5,"New Year", "Welcoming the upcoming year", "1st January", "https://cdn.7days.ru/pic/08e/984797/1462728/86.jpg", 112, 1));

        return holidays;
    }
}
