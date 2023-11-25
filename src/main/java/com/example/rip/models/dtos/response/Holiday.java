package com.example.rip.models.dtos.response;

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
public class Holiday {
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
