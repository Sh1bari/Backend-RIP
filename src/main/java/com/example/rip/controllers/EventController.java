package com.example.rip.controllers;

import com.example.rip.models.Event;
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
    public String getEvents(Model model, @RequestParam(name = "eventName", required = false, defaultValue = "") String name) {
        List<Event> Events = createSampleEvents();
        Events = Events.stream()
                .filter(o -> o.getName().toLowerCase().contains(name.toLowerCase()))
                .toList();
        model.addAttribute("Events", Events);

        List<MenuElement> menuElements = Arrays.asList(
                new MenuElement("Мероприятия", "http://localhost:8082/api/events")
        );
        model.addAttribute("menuElements", menuElements);
        model.addAttribute("defaultName", name);

        return "mainPage";
    }

    @GetMapping("/event/{id}")
    public String getEvent(Model model, @PathVariable(name = "id") Integer id) {
        List<Event> Events = createSampleEvents();
        Event Event = Events.stream().filter(o->o.getId().equals(id)).findFirst().orElse(null);
        List<MenuElement> menuElements = Arrays.asList(
                new MenuElement("Мероприятия", "http://localhost:8082/api/events"),
                new MenuElement(Event.getName(), "http://localhost:8082/api/event/" + id)
        );

        model.addAttribute("menuElements", menuElements);

        model.addAttribute("selectedEvent", Event);
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

    private List<Event> createSampleEvents() {
        List<Event> Events = new ArrayList<>();

        Events.add(new Event(1,"Лучший профорг", "Готовьтесь к волнующему событию – конкурсу 'Лучший Профорг'! Это уникальное мероприятие, созданное для поиска и выявления настоящих лидеров и организаторов в образовательном сообществе. Участвуйте в захватывающих испытаниях, где ваше мастерство в организации и мотивации будет подвергнуто проверке.\n" +
                "\n" +
                "Проявите свою креативность и лидерские навыки в разнообразных конкурсах, ориентированных на профессионализм и эффективность в роли профорганизатора. Будьте готовы к креативным заданиям, интенсивным играм и возможности продемонстрировать ваше понимание и поддержку студенческого сообщества.\n" +
                "\n" +
                "Лучший Профорг – это не только шанс победить и получить заслуженное признание, но и отличная возможность обменяться опытом, создать новые связи и вдохновиться идеями других талантливых профоргов. Примите вызов, докажите свою уникальность, и дайте миру увидеть, почему именно вы – лучший профорг в этом сезоне! Участвуйте, вдохновляйтесь, становитесь лучшими!", "25 Декабря", "https://leader-id.storage.yandexcloud.net/event_photo/267083/6206185bd3f35400596773.jpg", 30, 10));
        Events.add(new Event(2,"Вектор – танцы", "Команда Профсоюза факультета «Энергомашиностроение» совместно с действующими танцорами МГТУ им. Н.Э. Баумана приглашают принять участие в мастер-классе «Вектор – танцы».\n" +
                "\n" +
                "В начале мероприятия участники узнают об истории танца и возникновении различных стилей. Практическая часть мастер-класса включит в себя сразу 5 блоков: растяжку, танцевальный фитнес, k-pop, папинг, флешмоб.\n" +
                "\n" +
                "\uD83D\uDD38Когда: 9 декабря в 15:50\n" +
                "\uD83D\uDD38Где: СК МГТУ им. Н.Э. Баумана\n" +
                "\n" +
                "Хочешь развить координацию и пластичность, отвлечься от повседневной рутины и провести вечер в кругу единомышленников? Регистрируйся в форме и погружайся в мир творчества и танца!\n" +
                "\n" +
                "Всем участникам будут проставлены 1 посещение и 2 дополнительных балла по дисциплине «Физическая культура и спорт».\n" +
                "\n" +
                "Регистрация доступна по ссылке: https://forms.yandex.com/u/6558983dd046882e47e56799/\n" +
                "\n" +
                "По всем вопросам обращайтесь к Анастасии.", "9 Декабря", "https://sun9-36.userapi.com/impg/czobJWBJu_EsFVWIlY-bp46DgkbGRuKAMg8XPg/QRB7VGW24pM.jpg?size=807x807&quality=95&sign=7daf6736b95aaa52740e62312bc98584&c_uniq_tag=F-IkeNPh48ub--2NF0gythlhrnZZU4wR7P0RRhLzy3M&type=album", 40, 2));

        return Events;
    }
}
