package com.example.rip.controllers;

import com.example.rip.models.Holiday;
import com.example.rip.models.MenuElement;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class EventController {

    @GetMapping("/events")
    public String getEvents(Model model, @RequestParam(name = "name", required = false, defaultValue = "") String name) {
        List<Holiday> holidays = createSampleHolidays();
        holidays = holidays.stream()
                .filter(o -> o.getName().toLowerCase().contains(name.toLowerCase()))
                .toList();
        model.addAttribute("holidays", holidays);

        List<MenuElement> menuElements = Arrays.asList(
                new MenuElement("Мероприятия", "http://localhost:8082/api/events")
        );
        model.addAttribute("menuElements", menuElements);
        model.addAttribute("defaultName", name);

        return "mainPage";
    }

    @GetMapping("/event/{id}")
    public String getEvent(Model model, @PathVariable(name = "id") Integer id) {
        List<Holiday> holidays = createSampleHolidays();
        Holiday holiday = holidays.stream().filter(o->o.getId().equals(id)).findFirst().orElse(null);
        List<MenuElement> menuElements = Arrays.asList(
                new MenuElement("Мероприятия", "http://localhost:8082/api/events"),
                new MenuElement(holiday.getName(), "http://localhost:8082/api/event/" + id)
        );

        model.addAttribute("menuElements", menuElements);

        model.addAttribute("selectedEvent", holiday);
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
