package com.example.rip.models;

import lombok.*;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    private Integer id;
    private String name;
    private String description;
    private String date;
    private String imageUrl;
    private Integer tickets;
    private Integer purchasedTickets;

    // Геттеры и сеттеры

    // Конструктор
}
