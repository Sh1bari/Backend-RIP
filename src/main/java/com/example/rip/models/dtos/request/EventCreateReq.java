package com.example.rip.models.dtos.request;

import com.example.rip.models.entities.Event;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class EventCreateReq {
    @NotNull(message = "Имя не может быть пустым")
    private String name;

    @NotNull(message = "Описание не может быть пустым")
    private String description;

    @Min(value = 1, message = "Билетов не может быть меньше 1")
    @NotNull(message = "Поле билетов не должно ровняться null")
    private Integer tickets;

    @Future(message = "Время должно быть больше, чем сейчас")
    @NotNull(message = "Поле времени мероприятия не должно ровняться null")
    private LocalDateTime eventTime;

    public Event mapToEntity(){
        Event res = new Event();
        res.setName(name);
        res.setDescription(description);
        res.setTickets(tickets);
        res.setEventTime(eventTime);
        return res;
    }
}
