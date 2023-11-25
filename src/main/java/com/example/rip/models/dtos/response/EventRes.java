package com.example.rip.models.dtos.response;

import com.example.rip.models.entities.Event;
import com.example.rip.models.enums.ApplicationStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventRes {
    private Integer id;
    private String name;
    private String description;
    private String date;
    private Integer imageFileId;
    private Integer tickets;
    private Integer purchasedTickets;

    public static EventRes mapFromEntity(Event event){

        String pattern = "d MMMM HH:mm";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, new Locale("ru"));
        String formattedDateTime = event.getEventTime().format(formatter);

        EventRes builder = EventRes.builder()
                .id(event.getId())
                .name(event.getName())
                .description(event.getDescription())
                .date(formattedDateTime) // Преобразование LocalDateTime в строку
                .imageFileId(event.getFile() != null ? event.getFile().getId() : null)
                .tickets(event.getTickets())
                .purchasedTickets((int) event.getApplications().stream()
                        .filter(o->o.getStatus().equals(ApplicationStatus.COMPLETED))
                        .count())
                .build();
        return builder;
    }
}