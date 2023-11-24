package com.example.rip.controllers;

import com.example.rip.models.Holiday;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class EventController {

    @GetMapping("/events")
    public String getHolidays(Model model) {
        List<Holiday> holidays = createSampleHolidays();
        model.addAttribute("holidays", holidays);
        return "home";
    }

    private List<Holiday> createSampleHolidays() {
        List<Holiday> holidays = new ArrayList<>();

        holidays.add(new Holiday("Christmas", "Celebration of joy and happiness", "25th December", "https://cdn.7days.ru/pic/08e/984797/1462728/86.jpg"));
        holidays.add(new Holiday("New Year", "Welcoming the upcoming year", "1st January", "https://sakhalife.ru/wp-content/uploads/2023/01/novyj-god.png"));
        holidays.add(new Holiday("Christmas", "Celebration of joy and happiness", "25th December", "https://cdn.7days.ru/pic/08e/984797/1462728/86.jpg"));
        holidays.add(new Holiday("New Year", "Welcoming the upcoming year", "1st January", "https://cdn.7days.ru/pic/08e/984797/1462728/86.jpg"));
        holidays.add(new Holiday("New Year", "Welcoming the upcoming year", "1st January", "https://cdn.7days.ru/pic/08e/984797/1462728/86.jpg"));

        return holidays;
    }
}
